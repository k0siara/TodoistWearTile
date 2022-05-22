package com.patrykkosieradzki.todoist.wear.tile.repository

import com.patrykkosieradzki.todoist.wear.tile.domain.model.Label
import com.patrykkosieradzki.todoist.wear.tile.domain.model.Project
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistSyncData
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistSyncToken
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistSyncResourceType
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistTask
import com.patrykkosieradzki.todoist.wear.tile.domain.model.User
import com.patrykkosieradzki.todoist.wear.tile.network.api.TodoistApi
import com.patrykkosieradzki.todoist.wear.tile.network.model.sync.TodoistSyncRequest
import com.patrykkosieradzki.todoist.wear.tile.network.model.TodoistTaskResponse
import com.patrykkosieradzki.todoist.wear.tile.network.model.sync.LabelResponse
import com.patrykkosieradzki.todoist.wear.tile.network.model.sync.ProjectResponse
import com.patrykkosieradzki.todoist.wear.tile.network.model.sync.TodoistSyncResponse
import com.patrykkosieradzki.todoist.wear.tile.network.model.sync.TodoistSyncUserResponse
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

fun TodoistSyncResponse.toDomain() = TodoistSyncData(
    user = user.toDomain(),
    labels = labels.toDomain(),
    projects = projects.toDomain()
)

fun TodoistSyncUserResponse.toDomain() = User(
    fullName = fullName
)

@JvmName("toDomainLabelResponse")
fun List<LabelResponse>.toDomain() = map { it.toDomain() }

fun LabelResponse.toDomain() = Label(
    id = id,
    name = name,
    color = color,
    itemOrder = itemOrder == 1,
    isDeleted = isDeleted == 1,
    isFavorite = isFavorite == 1
)

@JvmName("toDomainProjectResponse")
fun List<ProjectResponse>.toDomain() = map { it.toDomain() }

fun ProjectResponse.toDomain() = Project(
    id = id,
    legacyId = legacyId,
    name = name,
    color = color,
    parentId = parentId,
    childOrder = childOrder,
    collapsed = collapsed == 1,
    shared = shared,
    legacyParentId = legacyParentId,
    syncId = syncId,
    isDeleted = isDeleted == 1,
    isArchived = isArchived == 1,
    isFavorite = isFavorite == 1
)


@JvmName("toDomainTodoistTaskResponse")
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
