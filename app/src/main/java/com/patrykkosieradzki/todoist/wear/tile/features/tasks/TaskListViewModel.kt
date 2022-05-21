package com.patrykkosieradzki.todoist.wear.tile.features.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.core.state.UiState
import com.patrykkosieradzki.composer.core.state.UiStateManager
import com.patrykkosieradzki.composer.core.state.uiStateManagerDelegate
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistTask
import com.patrykkosieradzki.todoist.wear.tile.domain.observer.ObserveTasks
import com.patrykkosieradzki.todoist.wear.tile.domain.usecase.FetchAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TaskListViewModel @Inject constructor(
    observeTasks: ObserveTasks,
    private val fetchAllTasksUseCase: FetchAllTasksUseCase
) : ViewModel(),
    UiStateManager by uiStateManagerDelegate(initialState = UiState.Loading) {

    val taskListComponents = observeTasks.flow
        .mapLatest { tasks ->
            when {
                tasks == null -> emptyList()
                tasks.isEmpty() -> listOf(TaskListScreenComponent.AddTaskButton)
                else -> {
                    val addButton = listOf(TaskListScreenComponent.AddTaskButton)
                    val taskItems = tasks.map { TaskListScreenComponent.TaskItem(it) }
                    addButton + taskItems
                }
            }
        }.asLiveData()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launchWithExceptionHandler(
            block = {
                updateUiStateToLoading()
                fetchAllTasksUseCase.invoke()
                updateUiStateToSuccess()
            },
            onFailure = { throwable ->
                Timber.e(throwable, "Error during loading tasks")
                updateUiStateToFailure(throwable)
            }
        )
    }

    fun onItemChecked(item: TodoistTask) {
//        tasksState.update { Async.Success(tasks) }
    }
}

sealed class TaskListScreenComponent {
    object AddTaskButton : TaskListScreenComponent()
    data class TaskItem(val todoistTask: TodoistTask) : TaskListScreenComponent()
}
