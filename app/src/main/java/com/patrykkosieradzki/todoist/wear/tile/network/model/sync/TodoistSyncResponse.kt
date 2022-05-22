package com.patrykkosieradzki.todoist.wear.tile.network.model.sync

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TodoistSyncResponse(
    val user: TodoistSyncUserResponse
)
