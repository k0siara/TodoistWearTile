package com.patrykkosieradzki.todoist.wear.tile.domain.model

data class TodoistSyncData(
    val user: User,
    val labels: List<Label>,
    val projects: List<Project>
)
