package com.patrykkosieradzki.todoist.wear.tile.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun putString(key: String, value: String?) {
        sharedPreferences.edit {
            putString(key, value)
        }
    }

    fun getString(key: String, defaultValue: String? = null) =
        sharedPreferences.getString(key, defaultValue)
}
