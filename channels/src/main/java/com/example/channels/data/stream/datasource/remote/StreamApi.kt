package com.example.channels.data.stream.datasource.remote

import com.example.channels.data.stream.datasource.remote.response.CreateNewStreamResponse
import com.example.channels.data.stream.datasource.remote.response.all.AllStreamResponse
import com.example.channels.data.stream.datasource.remote.response.subscribed.SubscribedStreamResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface StreamApi {
    @GET("streams")
    suspend fun getAllStreams(): AllStreamResponse

    @GET("users/me/subscriptions")
    suspend fun getSubscribedStreams(): SubscribedStreamResponse

    @POST("users/me/subscriptions")
    suspend fun createNewStream(
        @Query("subscriptions") query: String
    ): CreateNewStreamResponse
}
