package com.patrykkosieradzki.todoist.wear.tile.usecase

import com.patrykkosieradzki.todoist.wear.tile.auth.TokenStorage
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val tokenStorage: TokenStorage
) {
    fun invoke() = tokenStorage.accessToken != null
}
