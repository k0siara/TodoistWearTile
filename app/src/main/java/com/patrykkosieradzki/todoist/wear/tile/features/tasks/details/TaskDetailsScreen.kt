package com.patrykkosieradzki.todoist.wear.tile.features.tasks.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material.Text

@Composable
fun TaskDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskDetailsViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Task details...",
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}
