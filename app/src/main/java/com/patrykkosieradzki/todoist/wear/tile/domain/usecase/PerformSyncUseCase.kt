package com.patrykkosieradzki.todoist.wear.tile.domain.usecase

import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistSyncResource
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistSyncResourceType
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistSyncToken
import com.patrykkosieradzki.todoist.wear.tile.repository.TodoistRepository
import javax.inject.Inject

class PerformSyncUseCase @Inject constructor(
    private val todoistRepository: TodoistRepository
) {
    suspend fun invoke() {
        val data = todoistRepository.sync(
            todoistSyncToken = TodoistSyncToken.FullSync,
            resourceTypes = listOf(TodoistSyncResourceType(TodoistSyncResource.USER))
        )
        // store synced data
    }
}
