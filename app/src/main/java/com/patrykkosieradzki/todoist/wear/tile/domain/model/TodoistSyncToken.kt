package com.patrykkosieradzki.todoist.wear.tile.domain.model

sealed class TodoistSyncToken(
    val value: String
) {
    object FullSync : TodoistSyncToken("*")
    data class IncrementalSync(val lastToken: String) : TodoistSyncToken(lastToken)
}
