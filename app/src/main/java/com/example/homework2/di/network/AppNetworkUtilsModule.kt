package com.example.homework2.di.network

import com.example.homework2.BuildConfig
import com.example.homework2.di.qualifiers.BaseUrl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter

@Module
class AppNetworkUtilsModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String {
        return UrlProvider.url
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}
