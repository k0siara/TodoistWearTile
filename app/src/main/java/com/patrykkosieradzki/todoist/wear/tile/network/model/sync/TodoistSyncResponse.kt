package com.patrykkosieradzki.todoist.wear.tile.network.model.sync

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TodoistSyncResponse(

    @Json(name = "user")
    val user: TodoistSyncUserResponse,

    @Json(name = "labels")
    val labels: List<LabelResponse>,

    @Json(name = "projects")
    val projects: List<ProjectResponse>,

    @Json(name = "items")
    val tasks: List<TaskResponse>
)
