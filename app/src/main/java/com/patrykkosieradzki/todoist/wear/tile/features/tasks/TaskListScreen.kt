package com.patrykkosieradzki.todoist.wear.tile.features.tasks

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.items
import com.google.android.horologist.compose.navscaffold.ExperimentalHorologistComposeLayoutApi
import com.google.android.horologist.compose.navscaffold.scrollableColumn
import com.patrykkosieradzki.composer.composables.UiStateView
import com.patrykkosieradzki.composer.core.Async
import com.patrykkosieradzki.todoist.wear.tile.features.home.TodoistTaskItemWidget

@OptIn(ExperimentalHorologistComposeLayoutApi::class)
@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    listState: ScalingLazyListState,
    viewModel: TaskListViewModel
) {
    val viewState by viewModel.viewState.observeAsState(TaskListViewState.Empty)

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .scrollableColumn(focusRequester, listState),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState,
        contentPadding = PaddingValues(horizontal = 15.dp)
    ) {
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
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                    }

                    items(
                        items = list,
                        key = null,
                        itemContent = { item ->
                            TodoistTaskItemWidget(
                                modifier = Modifier.fillParentMaxWidth(),
                                todoistTask = item
                            )
                        }
                    )

                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                    }
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
    }

    UiStateView(
        uiStateManager = viewModel,
        renderOnSuccess = {

        }
    )
}
