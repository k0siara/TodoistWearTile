package com.patrykkosieradzki.todoist.wear.tile.repository

import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistTask
import com.patrykkosieradzki.todoist.wear.tile.network.TodoistApi
import com.patrykkosieradzki.todoist.wear.tile.network.TodoistTaskResponse
import javax.inject.Inject

class TodoistRepository @Inject constructor(
    private val todoistApi: TodoistApi
) {
    suspend fun getAllTasks(): List<TodoistTask> = todoistApi.getAllTasks().toDomain()
}

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
