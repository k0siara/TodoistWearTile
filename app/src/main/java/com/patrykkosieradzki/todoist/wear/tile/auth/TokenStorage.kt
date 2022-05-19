package com.patrykkosieradzki.todoist.wear.tile.auth

import com.patrykkosieradzki.todoist.wear.tile.storage.PreferenceManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStorage @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    var accessToken: String?
        get() = preferenceManager.getString("accessToken", null)
        set(value) {
            preferenceManager.putString("accessToken", value)
        }
}
