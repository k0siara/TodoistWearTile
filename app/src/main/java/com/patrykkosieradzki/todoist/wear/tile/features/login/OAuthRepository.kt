package com.patrykkosieradzki.todoist.wear.tile.features.login

import android.content.Context
import android.net.Uri
import androidx.wear.phone.interactions.authentication.CodeChallenge
import androidx.wear.phone.interactions.authentication.CodeVerifier
import androidx.wear.phone.interactions.authentication.OAuthRequest
import androidx.wear.phone.interactions.authentication.OAuthResponse
import androidx.wear.phone.interactions.authentication.RemoteAuthClient
import kotlinx.coroutines.suspendCancellableCoroutine

class OAuthRepository constructor(
    private val context: Context
) {
    private val remoteAuthClient = RemoteAuthClient.create(context)

    suspend fun authorize() {
        val request = OAuthRequest.Builder(context.packageName)
            .setAuthProviderUrl(Uri.parse("https://todoist.com/oauth/authorize?scope=data:read_write&state=123"))
            .setClientId("2bca0375698b4ef393d19a88a024e66b")
            .setCodeChallenge(CodeChallenge(CodeVerifier("123")))
            .build()

        return suspendCancellableCoroutine { continuation ->
            remoteAuthClient.sendAuthorizationRequest(
                request,
                object : RemoteAuthClient.Callback() {
                    override fun onAuthorizationResponse(
                        request: OAuthRequest,
                        response: OAuthResponse
                    ) {
                        // TODO: Handle all possible cases
                        when (response.getErrorCode()) {
                            RemoteAuthClient.ERROR_PHONE_UNAVAILABLE -> {
//                                continuation.resumeWithException()
                            }
                            RemoteAuthClient.ERROR_UNSUPPORTED -> {
//                                continuation.resumeWithException()
                            }
                            RemoteAuthClient.NO_ERROR -> {
//                                continuation.resumeWith()
                            }
                        }

                        println("onAuthorizationResponse: $request -> $response")
                    }

                    override fun onAuthorizationError(errorCode: Int) {
                        println("onAuthorizationError: $errorCode")
                    }
                }
            )
        }
    }
}
