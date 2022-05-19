package com.patrykkosieradzki.todoist.wear.tile.features.login

import android.content.Context
import android.net.Uri
import androidx.wear.phone.interactions.authentication.CodeChallenge
import androidx.wear.phone.interactions.authentication.CodeVerifier
import androidx.wear.phone.interactions.authentication.OAuthRequest
import androidx.wear.phone.interactions.authentication.OAuthResponse
import androidx.wear.phone.interactions.authentication.RemoteAuthClient
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

@Singleton
class OAuthManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
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

    private val remoteAuthClient = RemoteAuthClient.create(context)

    suspend fun authorize(
        clientId: String,
        verificationCode: String
    ): AuthorizationStatus {
        val oauthUriStr = OAUTH_AUTHORIZE_URL_TEMPLATE.format(
            READ_WRITE_SCOPE,
            verificationCode
        )

        val request = OAuthRequest.Builder(context)
            .setAuthProviderUrl(Uri.parse(oauthUriStr))
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

    companion object {
        private const val OAUTH_AUTHORIZE_URL_TEMPLATE =
            "https://todoist.com/oauth/authorize?scope=%s&state=%s"
        private const val READ_WRITE_SCOPE = "data:read_write"
        private const val STATE_QUERY_PARAM = "state"
        private const val CODE_QUERY_PARAM = "code"
    }
}
