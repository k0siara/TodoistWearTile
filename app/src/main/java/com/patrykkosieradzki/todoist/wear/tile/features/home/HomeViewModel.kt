package com.patrykkosieradzki.todoist.wear.tile.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.core.Async
import com.patrykkosieradzki.composer.core.event.ComposerFlowEvent
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistTask
import com.patrykkosieradzki.todoist.wear.tile.domain.usecase.GetAllTasksUseCase
import com.patrykkosieradzki.todoist.wear.tile.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    val navigateToLogin = ComposerFlowEvent<Unit>()

    private val tasksState = MutableStateFlow<Async<List<TodoistTask>>>(Async.Loading())
    private val appVersionState = MutableStateFlow("")

    val viewState = combine(
        tasksState,
        appVersionState
    ) { tasks, appVersion ->
        HomeViewState(
            tasks = tasks,
            appVersion = appVersion
        )
    }.asLiveData()

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

    fun onLogoutClicked() {
        viewModelScope.launchWithExceptionHandler(
            block = {
                logoutUseCase.invoke()
                navigateToLogin.fireEvent(Unit)
            },
            onFailure = { throwable ->
                Timber.e(throwable, "Error during logout")
                navigateToLogin.fireEvent(Unit)
            }
        )
    }
}

data class HomeViewState(
    val tasks: Async<List<TodoistTask>>,
    val appVersion: String
) {
    companion object {
        val Empty = HomeViewState(
            tasks = Async.Uninitialized,
            appVersion = ""
        )
    }
}