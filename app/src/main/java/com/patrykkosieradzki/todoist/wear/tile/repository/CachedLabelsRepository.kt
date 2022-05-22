package com.patrykkosieradzki.todoist.wear.tile.repository

import com.patrykkosieradzki.todoist.wear.tile.domain.model.Label
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Singleton
class CachedLabelsRepository @Inject constructor() {
    private val _labels by lazy { MutableStateFlow<List<Label>?>(null) }
    val flow by lazy { _labels.asStateFlow() }

    val isEmpty
        get() = flow.value == null

    fun cacheLabels(labels: List<Label>) {
        _labels.update { labels }
    }

    fun clear() {
        _labels.update { null }
    }
}
