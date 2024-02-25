package com.example.homework2.di.network

import com.example.homework2.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newHeaders = original.headers.newBuilder()
            .add("Authorization", BuildConfig.API_KEY)
            .build()
        return chain.proceed(
            original.newBuilder()
                .headers(newHeaders)
                .build()
        )
    }
}
