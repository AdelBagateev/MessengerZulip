package com.example.homework2.channels.data.topic.datasource.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class TopicByStreamIdResponse(
    val topics: List<Topic>
)
