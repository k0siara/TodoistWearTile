package com.patrykkosieradzki.todoist.wear.tile.features.login

import com.patrykkosieradzki.todoist.wear.tile.WearAppConfiguration
import com.patrykkosieradzki.todoist.wear.tile.WearException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val oAuthManager: OAuthManager,
    private val tokenRepository: TokenRepository,
    private val wearAppConfiguration: WearAppConfiguration,
    private val verificationCodeGenerator: VerificationCodeGenerator
) {
    suspend fun invoke() {
        try {
            val verificationCode = verificationCodeGenerator.createVerificationCode()
            val authStatus = oAuthManager.authorize(
                clientId = wearAppConfiguration.todoistClientId,
                verificationCode = verificationCode
            )
            when (authStatus) {
                OAuthManager.AuthorizationStatus.PhoneUnavailable -> {
                    throw WearException.PhoneUnavailableException()
                }
                OAuthManager.AuthorizationStatus.UnsupportedWatch -> {
                    throw WearException.UnsupportedWatchException()
                }
                is OAuthManager.AuthorizationStatus.Success -> {
                    val accessToken = tokenRepository.getAccessToken(
                        clientId = wearAppConfiguration.todoistClientId,
                        clientSecret = wearAppConfiguration.todoistClientSecret,
                        code = verificationCode
                    )
                    // store access token
                    // success :)
                }
            }
        } catch (we: WearException) {
            throw we
        } catch (e: Exception) {
            throw WearException.UnknownException(cause = e)
        }
    }
}
