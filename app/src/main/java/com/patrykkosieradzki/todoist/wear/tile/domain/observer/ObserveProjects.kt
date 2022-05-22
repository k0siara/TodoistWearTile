package com.patrykkosieradzki.todoist.wear.tile.domain.observer

import com.patrykkosieradzki.todoist.wear.tile.repository.CachedProjectsRepository
import javax.inject.Inject

class ObserveProjects @Inject constructor(
    private val cachedProjectsRepository: CachedProjectsRepository
) {
    val flow get() = cachedProjectsRepository.flow
}
