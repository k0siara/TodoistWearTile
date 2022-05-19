package com.patrykkosieradzki.todoist.wear.tile.features.login

import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val oAuthManager: OAuthManager
) {
    suspend fun invoke() {
        when (oAuthManager.authorize()) {
            is OAuthManager.AuthorizationStatus.Success -> TODO()
            OAuthManager.AuthorizationStatus.PhoneUnavailable -> TODO()
            OAuthManager.AuthorizationStatus.UnsupportedWatch -> TODO()
        }
    }
}
