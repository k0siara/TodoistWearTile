package com.patrykkosieradzki.todoist.wear.tile.domain.model

data class User(
    val fullName: String
) {
    val firstName: String
        get() {
            val split = fullName.split(" ")
            return when {
                split.isNotEmpty() -> split[0]
                else -> fullName
            }
        }
}