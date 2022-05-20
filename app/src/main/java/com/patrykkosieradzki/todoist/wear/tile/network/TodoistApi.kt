package com.patrykkosieradzki.todoist.wear.tile.network

import retrofit2.http.GET

interface TodoistApi {

    @GET("tasks")
    suspend fun getAllTasks(): List<TodoistTaskResponse>
}