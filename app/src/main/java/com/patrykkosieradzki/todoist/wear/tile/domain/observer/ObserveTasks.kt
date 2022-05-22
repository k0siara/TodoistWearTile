package com.patrykkosieradzki.todoist.wear.tile.domain.observer

import com.patrykkosieradzki.todoist.wear.tile.repository.TodoistCachedTasksRepository
import javax.inject.Inject

class ObserveTasks @Inject constructor(
    private val cachedTasksRepository: TodoistCachedTasksRepository
) {
    val flow get() = cachedTasksRepository.flow
    val isEmpty get() = cachedTasksRepository.isEmpty
}
