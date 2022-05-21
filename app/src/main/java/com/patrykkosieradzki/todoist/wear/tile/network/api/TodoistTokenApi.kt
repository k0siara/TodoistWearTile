package com.patrykkosieradzki.todoist.wear.tile.network.api

import com.patrykkosieradzki.todoist.wear.tile.network.model.AccessTokenResponse
import com.patrykkosieradzki.todoist.wear.tile.network.model.GetAccessTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface TodoistTokenApi {

    @POST("oauth/access_token")
    suspend fun getAccessToken(
        @Body body: GetAccessTokenRequest
    ): AccessTokenResponse
}
