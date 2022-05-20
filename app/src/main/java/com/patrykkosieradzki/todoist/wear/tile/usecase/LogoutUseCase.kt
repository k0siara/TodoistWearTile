package com.patrykkosieradzki.todoist.wear.tile.usecase

import com.patrykkosieradzki.todoist.wear.tile.auth.TokenStorage
import com.patrykkosieradzki.todoist.wear.tile.storage.PreferenceManager
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val preferenceManager: PreferenceManager
) {
    fun invoke() {
        tokenStorage.clear()
        preferenceManager.clear()
    }
}
