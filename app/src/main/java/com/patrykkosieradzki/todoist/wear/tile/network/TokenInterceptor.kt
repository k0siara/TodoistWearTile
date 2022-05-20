package com.patrykkosieradzki.todoist.wear.tile.network

import com.patrykkosieradzki.todoist.wear.tile.domain.TokenStorage
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val tokenStorage: TokenStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = tokenStorage.accessToken ?: return chain.proceed(chain.request())

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(request)
    }
}