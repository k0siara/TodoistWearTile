package com.patrykkosieradzki.todoist.wear.tile.features.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import com.patrykkosieradzki.composer.composables.ComposerFlowEventHandler

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    listState: ScalingLazyListState,
    viewModel: HomeViewModel,
    navigateToLogin: () -> Unit
) {
    val currentNavigateToLogin by rememberUpdatedState(newValue = navigateToLogin)

    ComposerFlowEventHandler(
        event = viewModel.navigateToLogin,
        handleEvent = { _, _ -> currentNavigateToLogin.invoke() }
    )

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        item {
            Text(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
                text = "You're logged in :)"
            )
        }

        item {
            Chip(
                modifier = Modifier.padding(top = 10.dp),
                label = {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Logout",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = viewModel::onLogoutClicked
            )
        }
    }
}
