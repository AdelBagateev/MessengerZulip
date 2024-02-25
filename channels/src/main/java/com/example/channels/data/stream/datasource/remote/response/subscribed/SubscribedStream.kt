package com.example.channels.data.stream.datasource.remote.response.subscribed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SubscribedStream(
    @SerialName("stream_id")
    val streamId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("color")
    val color: String,
)
