package com.patrykkosieradzki.todoist.wear.tile.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetAccessTokenRequest(
    @Json(name = "client_id") val clientId: String,
    @Json(name = "client_secret") val clientSecret: String,
    @Json(name = "code") val code: String
)
