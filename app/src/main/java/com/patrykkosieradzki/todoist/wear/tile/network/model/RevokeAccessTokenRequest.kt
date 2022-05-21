package com.patrykkosieradzki.todoist.wear.tile.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RevokeAccessTokenRequest(

    @Json(name = "client_id")
    val clientId: String,

    @Json(name = "client_secret")
    val clientSecret: String,

    @Json(name = "access_token")
    val accessToken: String
)

// {"client_id":"xyz", "client_secret":"xyz", "access_token":"xyz"}
