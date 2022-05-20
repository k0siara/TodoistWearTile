package com.patrykkosieradzki.todoist.wear.tile.domain.usecase

import com.patrykkosieradzki.todoist.wear.tile.domain.TokenStorage
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
