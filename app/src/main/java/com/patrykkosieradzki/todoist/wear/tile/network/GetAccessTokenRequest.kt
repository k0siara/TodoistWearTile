package com.patrykkosieradzki.todoist.wear.tile.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetAccessTokenRequest(
    @Json(name = "client_id") val clientId: String,
    @Json(name = "client_secret") val clientSecret: String,
    @Json(name = "code") val code: String,
    @Json(name = "redirect_uri") val redirectUri: String = "https://wear.googleapis.com/3p_auth/com.patrykkosieradzki.todoist.wear.tile"
)
