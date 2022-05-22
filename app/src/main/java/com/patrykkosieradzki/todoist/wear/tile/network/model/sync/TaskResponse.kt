package com.patrykkosieradzki.todoist.wear.tile.network.model.sync

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TaskResponse(
    @Json(name = "id")
    val id: String,

    @Json(name = "project_id")
    val projectId: String?,

    @Json(name = "section_id")
    val sectionId: String?,

    @Json(name = "labels")
    val labels: List<String>,

    @Json(name = "parentId")
    val parentId: String?,

    @Json(name = "content")
    val content: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "comment_count")
    val commentCount: Int?,

    @Json(name = "assignee")
    val assignee: String?,

    @Json(name = "assigner")
    val assigner: String?,

    @Json(name = "order")
    val order: Int?,

    @Json(name = "priority")
    val priority: Int,

    @Json(name = "url")
    val url: String?,

    @Json(name = "in_history")
    val inHistory: Int,

    @Json(name = "is_deleted")
    val isDeleted: Int
)

//{
//    "id": 301946961,
//    "legacy_id": 33511505,
//    "user_id": 1855589,
//    "project_id": 396936926,
//    "legacy_project_id": 128501470,
//    "content": "Task1",
//    "description": "",
//    "priority": 1,
//    "due": null,
//    "parent_id": null,
//    "legacy_parent_id": null,
//    "child_order": 1,
//    "section_id": null,
//    "day_order": -1,
//    "collapsed": 0,
//    "labels": [12839231, 18391839],
//    "added_by_uid": 1855589,
//    "assigned_by_uid": 1855589,
//    "responsible_uid": null,
//    "checked": 0,
//    "in_history": 0,
//    "is_deleted": 0,
//    "sync_id": null,
//    "date_added": "2014-09-26T08:25:05Z"
//}
