package com.patrykkosieradzki.todoist.wear.tile.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.todoist.wear.tile.domain.VersionProvider
import com.patrykkosieradzki.todoist.wear.tile.domain.model.Project
import com.patrykkosieradzki.todoist.wear.tile.domain.observer.ObserveLabels
import com.patrykkosieradzki.todoist.wear.tile.domain.observer.ObserveProjects
import com.patrykkosieradzki.todoist.wear.tile.domain.observer.ObserveTasks
import com.patrykkosieradzki.todoist.wear.tile.domain.observer.ObserveUser
import com.patrykkosieradzki.todoist.wear.tile.domain.usecase.PerformSyncUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    versionProvider: VersionProvider,
    observeUser: ObserveUser,
    observeTasks: ObserveTasks,
    observeLabels: ObserveLabels,
    observeProjects: ObserveProjects,
    private val performSyncUseCase: PerformSyncUseCase
) : ViewModel() {

    val viewState = combine(
        observeUser.flow,
        observeTasks.flow,
        observeLabels.flow,
        observeProjects.flow
    ) { user, tasks, labels, projects ->
        val favoriteLabels = labels?.filter { it.isFavorite } ?: emptyList()
        val favoriteLabelItems = favoriteLabels.map { label ->
            val taskCount = tasks?.count { it.labels.contains(label.id) } ?: 0

            FavoriteLabelItem(
                id = label.id,
                name = label.name,
                taskCount = taskCount
            )
        }

        HomeViewState(
            firstName = user?.firstName ?: "...",
            favoriteLabelItems = favoriteLabelItems,
            favoriteProjects = projects?.filter { it.isFavorite } ?: emptyList(),
            appVersion = versionProvider.versionName
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeViewState.Empty
    )

    init {
        sync()
    }

    private fun sync() {
        viewModelScope.launchWithExceptionHandler(
            block = {
                performSyncUseCase.invoke()
            },
            onFailure = { throwable ->
                Timber.e(throwable, "Error during sync on startup")
                // TODO: show error state with retry?
            }
        )
    }
}

data class HomeViewState(
    val firstName: String,
    val favoriteLabelItems: List<FavoriteLabelItem>,
    val favoriteProjects: List<Project>,
    val appVersion: String
) {
    companion object {
        val Empty = HomeViewState(
            firstName = "",
            favoriteLabelItems = emptyList(),
            favoriteProjects = emptyList(),
            appVersion = ""
        )
    }
}
