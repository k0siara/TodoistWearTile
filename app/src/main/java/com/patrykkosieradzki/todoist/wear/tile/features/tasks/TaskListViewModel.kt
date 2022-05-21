package com.patrykkosieradzki.todoist.wear.tile.features.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.core.Async
import com.patrykkosieradzki.composer.core.state.UiState
import com.patrykkosieradzki.composer.core.state.UiStateManager
import com.patrykkosieradzki.composer.core.state.uiStateManagerDelegate
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistTask
import com.patrykkosieradzki.todoist.wear.tile.domain.usecase.GetAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import timber.log.Timber

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase
) : ViewModel(),
    UiStateManager by uiStateManagerDelegate(initialState = UiState.Loading) {

    private val tasksState = MutableStateFlow<Async<List<TodoistTask>>>(Async.Loading())

    @OptIn(ExperimentalCoroutinesApi::class)
    val viewState = tasksState.mapLatest { TaskListViewState(it) }.asLiveData()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launchWithExceptionHandler(
            block = {
                tasksState.update { Async.Loading() }
                val tasks = getAllTasksUseCase.invoke()
                tasksState.update { Async.Success(tasks) }
            },
            onFailure = { throwable ->
                Timber.e(throwable, "Error during loading tasks")
                tasksState.update { Async.Fail(throwable) }
            }
        )
    }

    fun onItemChecked(item: TodoistTask) {
//        tasksState.update { Async.Success(tasks) }
    }
}

data class TaskListViewState(
    val tasks: Async<List<TodoistTask>>
) {
    companion object {
        val Empty = TaskListViewState(
            tasks = Async.Uninitialized
        )
    }
}