package com.patrykkosieradzki.todoist.wear.tile.features.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.items
import com.google.android.horologist.compose.navscaffold.ExperimentalHorologistComposeLayoutApi
import com.google.android.horologist.compose.navscaffold.scrollableColumn
import com.patrykkosieradzki.composer.composables.UiStateView
import com.patrykkosieradzki.todoist.wear.tile.features.home.TodoistTaskItemWidget

@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    listState: ScalingLazyListState,
    viewModel: TaskListViewModel,
    navigateToAddTask: () -> Unit,
    navigateToTaskDetails: (taskId: String) -> Unit
) {
    UiStateView(
        uiStateManager = viewModel,
        renderOnLoading = {
            TaskListLoading()
        },
        renderOnSuccess = {
            TaskListSuccess(
                focusRequester = focusRequester,
                listState = listState,
                viewModel = viewModel,
                navigateToAddTask = navigateToAddTask,
                navigateToTaskDetails = navigateToTaskDetails
            )
        },
        renderOnFailure = { throwable ->
            TaskListFailure(
                throwable = throwable,
                onTryAgainClicked = viewModel::onTryAgainClicked
            )
        }
    )
}

@Composable
private fun TaskListLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            indicatorColor = Color.White,
            trackColor = Color.Red
        )

        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "Loading...",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary
        )
    }
}

@OptIn(ExperimentalHorologistComposeLayoutApi::class)
@Composable
private fun TaskListSuccess(
    focusRequester: FocusRequester,
    listState: ScalingLazyListState,
    viewModel: TaskListViewModel,
    navigateToAddTask: () -> Unit,
    navigateToTaskDetails: (taskId: String) -> Unit
) {
    val taskListComponents by viewModel.taskListComponents.observeAsState(emptyList())

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .scrollableColumn(focusRequester, listState),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState,
        contentPadding = PaddingValues(horizontal = 15.dp)
    ) {
        items(
            items = taskListComponents,
            key = { component ->
                when (component) {
                    TaskListScreenComponent.AddTaskButton -> "add-task"
                    is TaskListScreenComponent.TaskItem -> component.todoistTask.id
                }
            }
        ) { component ->
            when (component) {
                TaskListScreenComponent.AddTaskButton -> {
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
                is TaskListScreenComponent.TaskItem -> {
                    TodoistTaskItemWidget(
                        modifier = Modifier.fillParentMaxWidth(),
                        todoistTask = component.todoistTask,
                        onClick = { navigateToTaskDetails(component.todoistTask.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskListFailure(
    throwable: Throwable,
    onTryAgainClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "Something went wrong :(",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary
        )
    }
}
