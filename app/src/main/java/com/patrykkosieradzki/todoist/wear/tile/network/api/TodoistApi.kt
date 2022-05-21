package com.patrykkosieradzki.todoist.wear.tile.network.api

import com.patrykkosieradzki.todoist.wear.tile.network.model.RevokeAccessTokenRequest
import com.patrykkosieradzki.todoist.wear.tile.network.model.TodoistTaskResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoistApi {

    @GET("sync/v8/sync")
    suspend fun getWithSync()

    @POST("sync/v8/access_tokens/revoke")
    suspend fun revokeAccessToken(
        @Body body: RevokeAccessTokenRequest
    ): Response<Unit>

    @GET("rest/v1/tasks")
    suspend fun getAllTasks(): List<TodoistTaskResponse>
}