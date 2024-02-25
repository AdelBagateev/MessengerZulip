package com.example.channels.data.stream.datasource.remote.response.all

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Stream(
    @SerialName("stream_id")
    val streamId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
)
