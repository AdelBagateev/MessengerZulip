package com.example.homework2.channels.data.stream.datasource.remote.response.all

import kotlinx.serialization.Serializable

@Serializable
data class AllStreamResponse(
    val streams: List<Stream>
)
