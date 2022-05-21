package com.patrykkosieradzki.todoist.wear.tile.repository

import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistTask
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Singleton
class TodoistCachedTasksRepository @Inject constructor() {
    private val _tasks by lazy { MutableStateFlow<List<TodoistTask>?>(null) }
    val flow by lazy { _tasks.asStateFlow() }

    val isEmpty
        get() = flow.value.isNullOrEmpty()

    fun cacheTasks(tasks: List<TodoistTask>) {
        _tasks.update { tasks }
    }

    fun clear() {
        _tasks.update { null }
    }
}
