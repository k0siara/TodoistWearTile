package com.patrykkosieradzki.todoist.wear.tile.repository

import com.patrykkosieradzki.todoist.wear.tile.domain.model.Project
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Singleton
class CachedProjectsRepository @Inject constructor() {
    private val _projects by lazy { MutableStateFlow<List<Project>?>(null) }
    val flow by lazy { _projects.asStateFlow() }

    val isEmpty
        get() = flow.value == null

    fun cacheProjects(labels: List<Project>) {
        _projects.update { labels }
    }

    fun clear() {
        _projects.update { null }
    }
}
