package com.patrykkosieradzki.todoist.wear.tile.features.splash

import androidx.lifecycle.ViewModel
import com.patrykkosieradzki.composer.core.event.ComposerFlowEvent
import com.patrykkosieradzki.todoist.wear.tile.domain.usecase.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isLoggedInUseCase: IsLoggedInUseCase
) : ViewModel() {

    val navigateToHome = ComposerFlowEvent<Unit>()
    val navigateToLogin = ComposerFlowEvent<Unit>()

    fun checkLoginStatus() {
        if (isLoggedInUseCase.invoke()) {
            navigateToHome.fireEvent(Unit)
        } else {
            navigateToLogin.fireEvent(Unit)
        }
    }
}
