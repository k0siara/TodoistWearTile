package com.patrykkosieradzki.todoist.wear.tile.repository

import com.patrykkosieradzki.todoist.wear.tile.network.GetAccessTokenRequest
import com.patrykkosieradzki.todoist.wear.tile.network.TodoistTokenApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
    private val tokenApi: TodoistTokenApi
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
}
