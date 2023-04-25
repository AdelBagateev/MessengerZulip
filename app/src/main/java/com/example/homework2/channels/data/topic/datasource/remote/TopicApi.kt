package com.example.homework2.channels.data.topic.datasource.remote

import com.example.homework2.channels.data.topic.datasource.remote.response.TopicByStreamIdResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TopicApi {
    @GET("users/me/{stream_id}/topics")
    suspend fun getTopicByStreamId(@Path("stream_id") streamId: Int): TopicByStreamIdResponse
}