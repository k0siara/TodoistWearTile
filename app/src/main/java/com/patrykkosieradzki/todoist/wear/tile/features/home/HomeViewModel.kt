package com.patrykkosieradzki.todoist.wear.tile.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.core.event.ComposerFlowEvent
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.todoist.wear.tile.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    val navigateToLogin = ComposerFlowEvent<Unit>()

    private val appVersionState = MutableStateFlow("1.0.0")

    val viewState = appVersionState.mapLatest {
        HomeViewState(
            appVersion = it
        )
    }.asLiveData()

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
    val appVersion: String
) {
    companion object {
        val Empty = HomeViewState(
            appVersion = ""
        )
    }
}