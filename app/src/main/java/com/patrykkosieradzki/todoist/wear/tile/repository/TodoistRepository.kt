package com.patrykkosieradzki.todoist.wear.tile.repository

import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistSyncData
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistSyncToken
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistSyncResourceType
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistTask
import com.patrykkosieradzki.todoist.wear.tile.network.api.TodoistApi
import com.patrykkosieradzki.todoist.wear.tile.network.model.sync.TodoistSyncRequest
import com.patrykkosieradzki.todoist.wear.tile.network.model.TodoistTaskResponse
import com.patrykkosieradzki.todoist.wear.tile.network.model.sync.TodoistSyncResponse
import javax.inject.Inject

class TodoistRepository @Inject constructor(
    private val todoistApi: TodoistApi
) {
    suspend fun sync(
        todoistSyncToken: TodoistSyncToken,
        resourceTypes: List<TodoistSyncResourceType>
    ): TodoistSyncData {
        return todoistApi.sync(
            TodoistSyncRequest(
                syncToken = todoistSyncToken.value,
                resourceTypes = resourceTypes.map { it.toString() }
            )
        ).toDomain()
    }

    suspend fun getAllTasks(): List<TodoistTask> = todoistApi.getAllTasks().toDomain()
}

fun TodoistSyncResponse.toDomain() = TodoistSyncData("")

fun List<TodoistTaskResponse>.toDomain() = map { it.toDomain() }

fun TodoistTaskResponse.toDomain() = TodoistTask(
    id = id,
    projectId = projectId,
    sectionId = sectionId,
    parentId = parentId,
    content = content,
    description = description,
    commentCount = commentCount,
    assignee = assignee,
    assigner = assigner,
    order = order,
    priority = priority,
    url = url
)
