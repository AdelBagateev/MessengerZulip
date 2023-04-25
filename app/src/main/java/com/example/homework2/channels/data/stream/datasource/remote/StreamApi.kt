package com.example.homework2.channels.data.stream.datasource.remote

import com.example.homework2.channels.data.stream.datasource.remote.response.all.AllStreamResponse
import com.example.homework2.channels.data.stream.datasource.remote.response.subscribed.SubscribedStreamResponse
import retrofit2.http.GET

interface StreamApi {
    @GET("streams")
    suspend fun getAllStreams(): AllStreamResponse

    @GET("users/me/subscriptions")
    suspend fun getSubscribedStreams(): SubscribedStreamResponse
}