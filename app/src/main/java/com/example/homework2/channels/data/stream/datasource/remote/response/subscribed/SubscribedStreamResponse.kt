package com.example.homework2.channels.data.stream.datasource.remote.response.subscribed

import kotlinx.serialization.Serializable

@Serializable
data class SubscribedStreamResponse(
    val subscriptions: List<SubscribedStream>
)
