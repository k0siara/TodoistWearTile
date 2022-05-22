package com.patrykkosieradzki.todoist.wear.tile.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.todoist.wear.tile.domain.VersionProvider
import com.patrykkosieradzki.todoist.wear.tile.domain.observer.ObserveUser
import com.patrykkosieradzki.todoist.wear.tile.domain.usecase.PerformSyncUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    versionProvider: VersionProvider,
    private val observeUser: ObserveUser,
    private val performSyncUseCase: PerformSyncUseCase
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val viewState = observeUser.flow.mapLatest { user ->
        HomeViewState(
            firstName = user?.firstName ?: "...",
            appVersion = versionProvider.versionName
        )
    }.asLiveData()

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
    val appVersion: String
) {
    companion object {
        val Empty = HomeViewState(
            firstName = "",
            appVersion = ""
        )
    }
}
