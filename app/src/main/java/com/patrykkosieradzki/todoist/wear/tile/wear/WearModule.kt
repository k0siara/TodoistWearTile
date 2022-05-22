package com.patrykkosieradzki.todoist.wear.tile.wear

import dagger.Binds
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module(
    includes = [
        WearModule.WearManagerModule::class,
    ]
)
@InstallIn(SingletonComponent::class)
object WearModule {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class WearManagerModule {
        @Binds
        abstract fun provideWearManager(impl: AndroidWearManager): WearManager

        @Binds
        abstract fun provideWearManagerHost(impl: AndroidWearManager): WearManagerHost
    }

}