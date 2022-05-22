package com.patrykkosieradzki.todoist.wear.tile.network.model.sync

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TodoistSyncRequest(

    @Json(name = "sync_token")
    private val syncToken: String,

    @Json(name = "resource_types")
    val resourceTypes: List<String>
)