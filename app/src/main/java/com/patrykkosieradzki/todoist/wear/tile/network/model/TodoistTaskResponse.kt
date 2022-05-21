package com.patrykkosieradzki.todoist.wear.tile.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TodoistTaskResponse(
    @Json(name = "id")
    val id: String,

    @Json(name = "projectId")
    val projectId: String?,

    @Json(name = "sectionId")
    val sectionId: String?,

    @Json(name = "parentId")
    val parentId: String?,

    @Json(name = "content")
    val content: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "commentCount")
    val commentCount: Int?,

    @Json(name = "assignee")
    val assignee: String?,

    @Json(name = "assigner")
    val assigner: String,

    @Json(name = "order")
    val order: Int,

    @Json(name = "priority")
    val priority: Int,

    @Json(name = "url")
    val url: String
)

//    "id": 2995104339,
//    "project_id": 2203306141,
//    "section_id": 7025,
//    "parent_id": 2995104589,
//    "content": "Buy Milk",
//    "description": "",
//    "comment_count": 10,
//    "assignee": 2671142,
//    "assigner": 2671362,
//    "order": 1,
//    "priority": 1,
//    "url": "https://todoist.com/showTask?id=2995104339"
