package com.patrykkosieradzki.todoist.wear.tile.features.home.confirmlogout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.core.event.ComposerFlowEvent
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.todoist.wear.tile.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
class ConfirmLogoutViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    val navigateToLogin = ComposerFlowEvent<Unit>()

    fun onLogoutConfirmed() {
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
