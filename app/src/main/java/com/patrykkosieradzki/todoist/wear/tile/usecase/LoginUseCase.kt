package com.patrykkosieradzki.todoist.wear.tile.usecase

import com.patrykkosieradzki.todoist.wear.tile.WearAppConfiguration
import com.patrykkosieradzki.todoist.wear.tile.WearException
import com.patrykkosieradzki.todoist.wear.tile.auth.OAuthManager
import com.patrykkosieradzki.todoist.wear.tile.repository.TokenRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val oAuthManager: OAuthManager,
    private val tokenRepository: TokenRepository,
    private val wearAppConfiguration: WearAppConfiguration,

    ) {
    suspend fun invoke() {
        try {
            val authStatus = oAuthManager.authorize(wearAppConfiguration.todoistClientId)
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
                        code = authStatus.code
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
