package com.patrykkosieradzki.todoist.wear.tile.domain.observer

import com.patrykkosieradzki.todoist.wear.tile.repository.CachedLabelsRepository
import javax.inject.Inject

class ObserveLabels @Inject constructor(
    private val cachedLabelsRepository: CachedLabelsRepository
) {
    val flow get() = cachedLabelsRepository.flow
}
