package com.patrykkosieradzki.todoist.wear.tile.domain.usecase

import com.patrykkosieradzki.todoist.wear.tile.repository.TodoistCachedTasksRepository
import com.patrykkosieradzki.todoist.wear.tile.repository.TodoistRepository
import javax.inject.Inject

class FetchAllTasksUseCase @Inject constructor(
    private val todoistRepository: TodoistRepository,
    private val cachedTasksRepository: TodoistCachedTasksRepository
) {
    suspend fun invoke(refresh: Boolean = false) {
        if (refresh || cachedTasksRepository.isEmpty) {
            cachedTasksRepository.clear()
            val tasks = todoistRepository.getAllTasks()
            cachedTasksRepository.cacheTasks(tasks)
        }
    }
}
