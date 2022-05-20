package com.patrykkosieradzki.todoist.wear.tile.features.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.items
import com.patrykkosieradzki.composer.composables.ComposerFlowEventHandler
import com.patrykkosieradzki.composer.core.Async

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    listState: ScalingLazyListState,
    viewModel: HomeViewModel,
    navigateToLogin: () -> Unit
) {
    val viewState by viewModel.viewState.observeAsState(HomeViewState.Empty)
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
                colors = ChipDefaults.primaryChipColors(),
                label = {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Add task",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = {

                }
            )
        }

        when (viewState.tasks) {
            Async.Uninitialized,
            is Async.Loading -> {
                item {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Loading...",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            is Async.Success -> {
                viewState.tasks.invoke()?.let { list ->
                    items(
                        items = list,
                        key = null,
                        itemContent = { item ->
                            Chip(
                                modifier = Modifier.padding(top = 10.dp),
                                colors = ChipDefaults.secondaryChipColors(),
                                label = {
                                    Text(
                                        modifier = Modifier.fillParentMaxWidth(),
                                        text = item.content,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                onClick = {}
                            )
                        }
                    )
                }
            }
            is Async.Fail -> {
                item {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Fail :<",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }


        item {
            Chip(
                modifier = Modifier.padding(top = 10.dp),
                colors = ChipDefaults.secondaryChipColors(),
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
