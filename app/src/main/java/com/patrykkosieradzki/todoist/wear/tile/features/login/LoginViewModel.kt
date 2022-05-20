package com.patrykkosieradzki.todoist.wear.tile.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.core.event.ComposerFlowEvent
import com.patrykkosieradzki.composer.core.state.UiState
import com.patrykkosieradzki.composer.core.state.UiStateManager
import com.patrykkosieradzki.composer.core.state.uiStateManagerDelegate
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.todoist.wear.tile.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel(),
    UiStateManager by uiStateManagerDelegate(initialState = UiState.Success) {

    val navigateToHomeEvent = ComposerFlowEvent<Unit>()

    fun onLoginClicked() {
        viewModelScope.launchWithExceptionHandler(
            block = {
                updateUiStateToLoading()
                loginUseCase.invoke()
                updateUiStateToSuccess()
                navigateToHomeEvent.fireEvent(Unit)
            },
            onFailure = { throwable ->
                Timber.e(throwable, "Error when logging in")
                updateUiStateToFailure(throwable)
            }
        )
    }
}
