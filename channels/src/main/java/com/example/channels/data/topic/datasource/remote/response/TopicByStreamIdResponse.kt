package com.example.channels.data.topic.datasource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TopicByStreamIdResponse(
    @SerialName("topics")
    val topics: List<Topic>
)
