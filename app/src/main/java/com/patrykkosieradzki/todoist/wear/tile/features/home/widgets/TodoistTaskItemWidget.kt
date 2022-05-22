package com.patrykkosieradzki.todoist.wear.tile.features.home.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.SplitToggleChip
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChipDefaults
import com.patrykkosieradzki.todoist.wear.tile.domain.model.TodoistTask

@Composable
fun TodoistTaskItemWidget(
    modifier: Modifier = Modifier,
    todoistTask: TodoistTask,
    onClick: () -> Unit = {},
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    SplitToggleChip(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        label = {
            Text(
                text = todoistTask.content,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        toggleControl = {
            Icon(
                imageVector = ToggleChipDefaults.checkboxIcon(checked = checked),
                contentDescription = if (checked) "Checked" else "Unchecked"
            )
        },
        onClick = onClick
    )
}
