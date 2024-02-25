package com.example.channels.data.stream.datasource.remote.response.all

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AllStreamResponse(
    @SerialName("streams")
    val streams: List<Stream>
)
