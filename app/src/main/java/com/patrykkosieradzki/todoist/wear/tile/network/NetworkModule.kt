package com.patrykkosieradzki.todoist.wear.tile.network

import com.patrykkosieradzki.todoist.wear.tile.WearAppConfiguration
import com.patrykkosieradzki.todoist.wear.tile.domain.TokenStorage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TokenRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TodoistRetrofit

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        tokenStorage: TokenStorage
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(TokenInterceptor(tokenStorage))
            .build()
    }

    @TokenRetrofit
    @Singleton
    @Provides
    fun provideTokenRetrofit(
        appConfiguration: WearAppConfiguration,
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(appConfiguration.todoistTokenApiUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @TodoistRetrofit
    @Singleton
    @Provides
    fun provideTodoistRetrofit(
        appConfiguration: WearAppConfiguration,
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(appConfiguration.todoistApiUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideTokenApi(@TokenRetrofit retrofit: Retrofit): TodoistTokenApi {
        return retrofit.create(TodoistTokenApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTodoistApi(@TodoistRetrofit retrofit: Retrofit): TodoistApi {
        return retrofit.create(TodoistApi::class.java)
    }
}
