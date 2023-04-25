package com.example.homework2.global_di.network

import com.example.homework2.common.ApiKeyInterceptor
import com.example.homework2.global_di.qualifiers.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module(includes = [AppNetworkUtilsModule::class])
class AppNetworkModule {

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
