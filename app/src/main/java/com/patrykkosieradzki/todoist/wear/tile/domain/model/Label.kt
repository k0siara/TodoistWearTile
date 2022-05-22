package com.patrykkosieradzki.todoist.wear.tile.domain.model

data class Label(
    val id: String,
    val name: String,
    val color: Int,
    val itemOrder: Boolean,
    val isDeleted: Boolean,
    val isFavorite: Boolean,
)
