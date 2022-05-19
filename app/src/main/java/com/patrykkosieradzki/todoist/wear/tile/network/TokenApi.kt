package com.patrykkosieradzki.todoist.wear.tile.network

import retrofit2.http.Body
import retrofit2.http.POST

interface TokenApi {

    @POST("oauth/access_token")
    suspend fun getAccessToken(
        @Body request: GetAccessTokenRequest
    ): AccessTokenResponse
}
