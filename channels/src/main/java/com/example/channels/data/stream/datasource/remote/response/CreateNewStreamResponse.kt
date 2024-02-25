package com.example.channels.data.stream.datasource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreateNewStreamResponse(
    @SerialName("msg")
    val message: String
)
