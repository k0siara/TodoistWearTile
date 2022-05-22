package com.patrykkosieradzki.todoist.wear.tile.network.model.sync

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LabelResponse(

    @Json(name = "id")
    val id: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "color")
    val color: Int,

    @Json(name = "item_order")
    val itemOrder: Int,

    @Json(name = "is_deleted")
    val isDeleted: Int,

    @Json(name = "is_favorite")
    val isFavorite: Int,
)
