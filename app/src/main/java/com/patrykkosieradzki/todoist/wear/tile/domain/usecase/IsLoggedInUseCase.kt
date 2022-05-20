package com.patrykkosieradzki.todoist.wear.tile.domain.usecase

import com.patrykkosieradzki.todoist.wear.tile.domain.TokenStorage
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val tokenStorage: TokenStorage
) {
    fun invoke() = tokenStorage.accessToken != null
}
