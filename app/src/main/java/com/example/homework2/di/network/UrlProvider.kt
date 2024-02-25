package com.example.homework2.di.network

import com.example.homework2.BuildConfig

object UrlProvider {
    var url = BuildConfig.API_ENDPOINT

    fun reset() {
        url = BuildConfig.API_ENDPOINT
    }
}
