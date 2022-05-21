package com.patrykkosieradzki.todoist.wear.tile.domain.usecase

import com.patrykkosieradzki.todoist.wear.tile.WearAppConfiguration
import com.patrykkosieradzki.todoist.wear.tile.domain.TokenStorage
import com.patrykkosieradzki.todoist.wear.tile.repository.AuthRepository
import com.patrykkosieradzki.todoist.wear.tile.storage.PreferenceManager
import java.util.concurrent.CancellationException
import javax.inject.Inject
import timber.log.Timber

class LogoutUseCase @Inject constructor(
    private val appConfiguration: WearAppConfiguration,
    private val authRepository: AuthRepository,
    private val tokenStorage: TokenStorage,
    private val preferenceManager: PreferenceManager
) {
    suspend fun invoke() {
        try {
            val accessToken = tokenStorage.accessToken
            if (accessToken != null) {
                authRepository.revokeAccessToken(
                    clientId = appConfiguration.todoistClientId,
                    clientSecret = appConfiguration.todoistClientSecret,
                    accessToken = accessToken
                )
            } else {
                Timber.d("Access token not found during logout")
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e, "Error during logging out")
        }

        tokenStorage.clear()
        preferenceManager.clear()
    }
}
