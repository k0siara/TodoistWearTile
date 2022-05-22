package com.patrykkosieradzki.todoist.wear.tile.features.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.todoist.wear.tile.wear.WearManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val wearManager: WearManager
) : ViewModel() {

    fun getTextFromSpeech() {
        viewModelScope.launchWithExceptionHandler {
            val text = wearManager.getTextFromSpeechAsync().await()
        }
    }
}
