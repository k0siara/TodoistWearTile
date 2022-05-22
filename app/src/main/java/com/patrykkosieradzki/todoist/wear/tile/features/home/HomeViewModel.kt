package com.patrykkosieradzki.todoist.wear.tile.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.todoist.wear.tile.domain.VersionProvider
import com.patrykkosieradzki.todoist.wear.tile.domain.usecase.PerformSyncUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import timber.log.Timber

@HiltViewModel
class HomeViewModel @Inject constructor(
    versionProvider: VersionProvider,
    private val performSyncUseCase: PerformSyncUseCase
) : ViewModel() {

    val appVersion = versionProvider.versionName

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
