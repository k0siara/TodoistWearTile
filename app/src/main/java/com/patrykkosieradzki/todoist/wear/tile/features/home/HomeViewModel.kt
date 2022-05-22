package com.patrykkosieradzki.todoist.wear.tile.features.home

import androidx.lifecycle.ViewModel
import com.patrykkosieradzki.todoist.wear.tile.domain.VersionProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    versionProvider: VersionProvider
) : ViewModel() {

    val appVersion = versionProvider.versionName
}
