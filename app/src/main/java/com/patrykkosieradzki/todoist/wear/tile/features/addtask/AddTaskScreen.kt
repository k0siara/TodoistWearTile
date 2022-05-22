package com.patrykkosieradzki.todoist.wear.tile.features.addtask

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun AddTaskScreen(
    modifier: Modifier = Modifier,
    viewModel: AddTaskViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.getTextFromSpeech()
    }
}
