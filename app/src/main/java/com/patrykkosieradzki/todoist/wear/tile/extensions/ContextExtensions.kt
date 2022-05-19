package com.patrykkosieradzki.todoist.wear.tile.extensions

import android.content.Context
import android.content.ContextWrapper
import androidx.wear.widget.ConfirmationOverlay
import com.patrykkosieradzki.todoist.wear.tile.MainActivity

fun Context.findActivity(): MainActivity? = when (this) {
    is MainActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun Context.showConfirmationOverlay(
    type: Int = ConfirmationOverlay.OPEN_ON_PHONE_ANIMATION,
    message: String,
    duration: Int = 1500,
    onAnimationFinished: () -> Unit = {}
) {
    findActivity()?.let { activity ->
        val messageCharSequence: CharSequence = message

        ConfirmationOverlay()
            .setType(type)
            .setDuration(duration)
            .setMessage(messageCharSequence)
            .setOnAnimationFinishedListener {
                onAnimationFinished.invoke()
            }.showOn(activity)
    }
}
