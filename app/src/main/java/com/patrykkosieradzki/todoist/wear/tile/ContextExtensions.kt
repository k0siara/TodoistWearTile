package com.patrykkosieradzki.todoist.wear.tile

import android.content.Context
import android.content.ContextWrapper

fun Context.findActivity(): MainActivity? = when (this) {
    is MainActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
