package com.patrykkosieradzki.todoist.wear.tile.features.addtask

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.startActivityForResult
import com.patrykkosieradzki.todoist.wear.tile.extensions.findActivity

@Composable
fun AddTaskScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }

        context.findActivity()?.let { activity ->
            startActivityForResult(activity, intent, 42, null)
        }
    }
}
