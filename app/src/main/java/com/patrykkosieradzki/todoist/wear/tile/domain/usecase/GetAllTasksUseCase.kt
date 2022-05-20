package com.patrykkosieradzki.todoist.wear.tile.domain.usecase

import com.patrykkosieradzki.todoist.wear.tile.repository.TodoistRepository
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val todoistRepository: TodoistRepository
) {
    suspend fun invoke() = todoistRepository.getAllTasks()
}
