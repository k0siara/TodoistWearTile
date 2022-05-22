package com.patrykkosieradzki.todoist.wear.tile.repository

import com.patrykkosieradzki.todoist.wear.tile.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Singleton
class CachedUserRepository @Inject constructor() {
    private val _user by lazy { MutableStateFlow<User?>(null) }
    val flow by lazy { _user.asStateFlow() }

    val isEmpty
        get() = flow.value == null

    fun cacheUser(user: User) {
        _user.update { user }
    }

    fun clear() {
        _user.update { null }
    }
}
