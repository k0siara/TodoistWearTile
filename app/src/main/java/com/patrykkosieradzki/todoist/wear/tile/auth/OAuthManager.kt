package com.patrykkosieradzki.todoist.wear.tile.auth

import android.content.Context
import android.net.Uri
import androidx.wear.phone.interactions.authentication.CodeChallenge
import androidx.wear.phone.interactions.authentication.CodeVerifier
import androidx.wear.phone.interactions.authentication.OAuthRequest
import androidx.wear.phone.interactions.authentication.OAuthResponse
import androidx.wear.phone.interactions.authentication.RemoteAuthClient
import com.patrykkosieradzki.todoist.wear.tile.domain.VerificationCodeGenerator
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

@Singleton
class OAuthManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val verificationCodeGenerator: VerificationCodeGenerator
) {
    private val remoteAuthClient = RemoteAuthClient.create(context)

    suspend fun authorize(clientId: String): AuthorizationStatus {
        val verificationCode = verificationCodeGenerator.createVerificationCode()

        val uri = Uri.Builder()
            .encodedPath(OAUTH_AUTHORIZE_URL)
            .appendQueryParameter(SCOPE_QUERY_PARAM, READ_WRITE_SCOPE)
            .build()

        val request = OAuthRequest.Builder(context)
            .setAuthProviderUrl(uri)
            .setClientId(clientId)
            .setCodeChallenge(CodeChallenge(CodeVerifier(verificationCode)))
            .build()

        return suspendCancellableCoroutine { continuation ->
            remoteAuthClient.sendAuthorizationRequest(
                request,
                Executors.newSingleThreadExecutor(),
                object : RemoteAuthClient.Callback() {
                    override fun onAuthorizationResponse(
                        request: OAuthRequest,
                        response: OAuthResponse
                    ) {
                        runCatching {
                            val url = response.responseUrl ?: throw IllegalStateException()
                            val state = url.getQueryParameter(STATE_QUERY_PARAM)
                                ?: throw IllegalStateException()
                            val code = url.getQueryParameter(CODE_QUERY_PARAM)
                                ?: throw IllegalStateException()

                            if (state != verificationCode) {
                                throw IllegalStateException()
                            }
                            continuation.resume(AuthorizationStatus.Success(code))

                        }.onFailure { throwable ->
                            continuation.resumeWithException(
                                OAuthManagerException.UnknownException(
                                    message = "Error while retrieving authorization code",
                                    cause = throwable
                                )
                            )
                        }
                    }

                    override fun onAuthorizationError(request: OAuthRequest, errorCode: Int) {
                        when (errorCode) {
                            RemoteAuthClient.ERROR_PHONE_UNAVAILABLE -> {
                                continuation.resume(AuthorizationStatus.PhoneUnavailable)
                            }
                            RemoteAuthClient.ERROR_UNSUPPORTED -> {
                                continuation.resume(AuthorizationStatus.UnsupportedWatch)
                            }
                            else -> {
                                continuation.resumeWithException(
                                    OAuthManagerException.UnknownException(
                                        message = "AuthorizationError with unknown errorCode: $errorCode",
                                        cause = null
                                    )
                                )
                            }
                        }
                    }
                }
            )
        }
    }

    fun destroy() {
        remoteAuthClient.close()
    }

    sealed class AuthorizationStatus {
        data class Success(val code: String) : AuthorizationStatus()
        object PhoneUnavailable : AuthorizationStatus()
        object UnsupportedWatch : AuthorizationStatus()
    }

    sealed class OAuthManagerException(message: String, cause: Throwable? = null) :
        Exception(message, cause) {

        class UnknownException(message: String, cause: Throwable?) :
            OAuthManagerException(message, cause)
    }

    companion object {
        private const val OAUTH_AUTHORIZE_URL = "https://todoist.com/oauth/authorize"
        private const val SCOPE_QUERY_PARAM = "scope"
        private const val READ_WRITE_SCOPE = "data:read_write"
        private const val STATE_QUERY_PARAM = "state"
        private const val CODE_QUERY_PARAM = "code"
    }
}
