package com.patrykkosieradzki.todoist.wear.tile.domain.usecase

import com.patrykkosieradzki.todoist.wear.tile.WearAppConfiguration
import com.patrykkosieradzki.todoist.wear.tile.WearException
import com.patrykkosieradzki.todoist.wear.tile.auth.OAuthManager
import com.patrykkosieradzki.todoist.wear.tile.domain.TokenStorage
import com.patrykkosieradzki.todoist.wear.tile.repository.AuthRepository
import java.util.concurrent.CancellationException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val oAuthManager: OAuthManager,
    private val authRepository: AuthRepository,
    private val tokenStorage: TokenStorage,
    private val wearAppConfiguration: WearAppConfiguration,
) {
    suspend fun invoke() {
        try {
            when (val authStatus = oAuthManager.authorize(wearAppConfiguration.todoistClientId)) {
                OAuthManager.AuthorizationStatus.PhoneUnavailable -> {
                    throw WearException.PhoneUnavailableException()
                }
                OAuthManager.AuthorizationStatus.UnsupportedWatch -> {
                    throw WearException.UnsupportedWatchException()
                }
                is OAuthManager.AuthorizationStatus.Success -> {
                    val accessToken = authRepository.getAccessToken(
                        clientId = wearAppConfiguration.todoistClientId,
                        clientSecret = wearAppConfiguration.todoistClientSecret,
                        code = authStatus.code
                    )
                    tokenStorage.accessToken = accessToken
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: WearException) {
            throw e
        } catch (e: Exception) {
            throw WearException.UnknownException(cause = e)
        }
    }
}
