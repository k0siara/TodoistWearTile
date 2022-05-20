package com.patrykkosieradzki.todoist.wear.tile.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.core.event.ComposerFlowEvent
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.todoist.wear.tile.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    val navigateToLogin = ComposerFlowEvent<Unit>()

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
