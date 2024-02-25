package com.example.homework2.di.network

import com.example.homework2.di.qualifiers.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [AppNetworkUtilsModule::class])
class AppNetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        @BaseUrl baseUrl: String,
    ): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .connectTimeout(10L, TimeUnit.SECONDS)
            .build()
    }
}
