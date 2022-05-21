package com.patrykkosieradzki.todoist.wear.tile.domain

import com.patrykkosieradzki.todoist.wear.tile.BuildConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VersionProvider @Inject constructor() {
    val versionName
        get() = BuildConfig.VERSION_NAME
}
