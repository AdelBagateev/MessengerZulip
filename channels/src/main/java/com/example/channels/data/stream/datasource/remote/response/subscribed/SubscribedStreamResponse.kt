package com.example.channels.data.stream.datasource.remote.response.subscribed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SubscribedStreamResponse(
    @SerialName("subscriptions")
    val subscriptions: List<SubscribedStream>
)
