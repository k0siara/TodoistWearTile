package com.patrykkosieradzki.todoist.wear.tile.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import com.google.android.horologist.compose.navscaffold.ExperimentalHorologistComposeLayoutApi
import com.google.android.horologist.compose.navscaffold.scrollableColumn
import com.patrykkosieradzki.composer.composables.ComposerFlowEventHandler

@OptIn(ExperimentalHorologistComposeLayoutApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    listState: ScalingLazyListState,
    viewModel: HomeViewModel,
    navigateToAddTask: () -> Unit,
    navigateToTasks: () -> Unit,
    navigateToLogin: () -> Unit
) {
    val currentNavigateToLogin by rememberUpdatedState(newValue = navigateToLogin)

    ComposerFlowEventHandler(
        event = viewModel.navigateToLogin,
        handleEvent = { _, _ -> currentNavigateToLogin.invoke() }
    )

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .scrollableColumn(focusRequester, listState),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState,
        contentPadding = PaddingValues(start = 15.dp, top = 20.dp, end = 15.dp)
    ) {
        item("title") {
            ListHeader(
                modifier = Modifier.fillParentMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillParentMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Hello, Patryk,",
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "anything TODO?",
                        maxLines = 1,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        item("add-task") {
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
                onClick = navigateToAddTask
            )
        }

        item("all-tasks") {
            Chip(
                modifier = Modifier.padding(top = 10.dp),
                colors = ChipDefaults.secondaryChipColors(),
                label = {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "All tasks",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = navigateToTasks
            )
        }

        item("account-header") {
            ListHeader(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    modifier = Modifier.fillParentMaxWidth(),
                    text = "Account",
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        item("settings") {
            Chip(
                colors = ChipDefaults.secondaryChipColors(),
                label = {
                    Text(
                        modifier = Modifier.fillParentMaxWidth(),
                        text = "Settings (in progress)",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                onClick = viewModel::onLogoutClicked,
                enabled = false
            )
        }

        item("logout") {
            Chip(
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

        item("app-version") {
            ListHeader(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    modifier = Modifier.fillParentMaxWidth(),
                    text = "App Version: ${viewModel.appVersion}",
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
