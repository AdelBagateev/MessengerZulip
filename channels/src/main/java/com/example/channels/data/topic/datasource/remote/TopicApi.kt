package com.example.channels.data.topic.datasource.remote

import com.example.channels.data.topic.datasource.remote.response.TopicByStreamIdResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface TopicApi {
    @GET("users/me/{stream_id}/topics")
    suspend fun getTopicByStreamId(@Path("stream_id") streamId: Int): TopicByStreamIdResponse
}
