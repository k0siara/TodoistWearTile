package com.patrykkosieradzki.todoist.wear.tile.domain.model

data class Project(
    val id: String,
    val legacyId: String?,
    val name: String,
    val color: Int,
    val parentId: String?,
    val childOrder: Int,
    val collapsed: Boolean,
    val shared: Boolean,
    val legacyParentId: String?,
    val syncId: String?,
    val isDeleted: Boolean,
    val isArchived: Boolean,
    val isFavorite: Boolean
)