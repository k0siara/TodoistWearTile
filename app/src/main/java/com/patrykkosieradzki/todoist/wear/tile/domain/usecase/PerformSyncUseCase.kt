package com.patrykkosieradzki.todoist.wear.tile.domain.usecase

import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistSyncResource
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistSyncResourceType
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistSyncToken
import com.patrykkosieradzki.todoist.wear.tile.repository.CachedUserRepository
import com.patrykkosieradzki.todoist.wear.tile.repository.TodoistRepository
import javax.inject.Inject

class PerformSyncUseCase @Inject constructor(
    private val todoistRepository: TodoistRepository,
    private val cachedUserRepository: CachedUserRepository
) {
    suspend fun invoke() {
        // TODO: add full sync only after login
        //  if sync token is available then perform partial sync on startup

        val data = todoistRepository.sync(
            todoistSyncToken = TodoistSyncToken.FullSync,
            resourceTypes = listOf(TodoistSyncResourceType(TodoistSyncResource.USER))
        )
        cachedUserRepository.cacheUser(data.user)
    }
}
