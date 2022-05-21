package com.patrykkosieradzki.todoist.wear.tile.features.tasks

data class TasksScreenState(
    val components: List<TasksScreenComponent>
)

sealed class TasksScreenComponent {
    object Loading : TasksScreenComponent()
}
