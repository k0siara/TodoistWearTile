package com.patrykkosieradzki.todoist.wear.tile.network.model.sync

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProjectResponse(
    @Json(name = "id")
    val id: String,

    @Json(name = "legacy_id")
    val legacyId: String?,

    @Json(name = "name")
    val name: String,

    @Json(name = "color")
    val color: Int,

    @Json(name = "parent_id")
    val parentId: String?,

    @Json(name = "child_order")
    val childOrder: Int,

    @Json(name = "collapsed")
    val collapsed: Int,

    @Json(name = "shared")
    val shared: Boolean,

    @Json(name = "legacy_parent_id")
    val legacyParentId: String?,

    @Json(name = "syncId")
    val syncId: String?,

    @Json(name = "is_deleted")
    val isDeleted: Int,

    @Json(name = "is_archived")
    val isArchived: Int,

    @Json(name = "is_favorite")
    val isFavorite: Int
)
