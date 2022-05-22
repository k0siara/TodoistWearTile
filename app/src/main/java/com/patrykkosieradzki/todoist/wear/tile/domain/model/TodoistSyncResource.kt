package com.patrykkosieradzki.todoist.wear.tile.domain.model

enum class TodoistSyncResource(
    val value: String
) {
    ALL("all"),
    USER("user"),
    LABELS("labels"),
    PROJECTS("projects"),
    TASKS("items")
}

data class TodoistSyncResourceType(
    val type: TodoistSyncResource,
    val exclude: Boolean = false
) {
    override fun toString() = "${if (exclude) "-" else ""}${type.value}"
}
