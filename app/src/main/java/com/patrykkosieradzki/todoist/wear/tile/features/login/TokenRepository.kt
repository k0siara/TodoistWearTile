package com.patrykkosieradzki.todoist.wear.tile.features.login

import com.patrykkosieradzki.todoist.wear.tile.WearAppConfiguration
import com.patrykkosieradzki.todoist.wear.tile.network.GetAccessTokenRequest
import com.patrykkosieradzki.todoist.wear.tile.network.TokenApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(
    private val wearAppConfiguration: WearAppConfiguration,
    private val tokenApi: TokenApi
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
