package com.patrykkosieradzki.todoist.wear.tile.network.model.sync

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TodoistSyncUserResponse(

    @Json(name = "id")
    val id: String,

    @Json(name = "full_name")
    val fullName: String
)
