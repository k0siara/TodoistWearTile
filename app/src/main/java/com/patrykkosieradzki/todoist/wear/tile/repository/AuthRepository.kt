package com.patrykkosieradzki.todoist.wear.tile.repository

import com.patrykkosieradzki.todoist.wear.tile.network.model.GetAccessTokenRequest
import com.patrykkosieradzki.todoist.wear.tile.network.api.TodoistApi
import com.patrykkosieradzki.todoist.wear.tile.network.api.TodoistTokenApi
import com.patrykkosieradzki.todoist.wear.tile.network.model.RevokeAccessTokenRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val tokenApi: TodoistTokenApi,
    private val todoistApi: TodoistApi
) {
    suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): String {
        return tokenApi.getAccessToken(
            GetAccessTokenRequest(
                clientId = clientId,
                clientSecret = clientSecret,
                code = code
            )
        ).accessToken
    }

    suspend fun revokeAccessToken(
        clientId: String,
        clientSecret: String,
        accessToken: String
    ) {
        todoistApi.revokeAccessToken(
            RevokeAccessTokenRequest(
                clientId = clientId,
                clientSecret = clientSecret,
                accessToken = accessToken
            )
        )
    }
}
