package com.patrykkosieradzki.todoist.wear.tile.domain.model

data class TodoistTask(
    val id: String,
    val projectId: String?,
    val sectionId: String?,
    val labels: List<String>,
    val parentId: String?,
    val content: String,
    val description: String,
    val commentCount: Int?,
    val assignee: String?,
    val assigner: String?,
    val order: Int?,
    val priority: Int,
    val url: String?
)
