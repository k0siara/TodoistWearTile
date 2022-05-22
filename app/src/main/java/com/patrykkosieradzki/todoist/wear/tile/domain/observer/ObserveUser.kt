package com.patrykkosieradzki.todoist.wear.tile.domain.observer

import com.patrykkosieradzki.todoist.wear.tile.repository.CachedUserRepository
import javax.inject.Inject

class ObserveUser @Inject constructor(
    private val cachedUserRepository: CachedUserRepository
) {
    val flow get() = cachedUserRepository.flow
    val isEmpty get() = cachedUserRepository.isEmpty
}
