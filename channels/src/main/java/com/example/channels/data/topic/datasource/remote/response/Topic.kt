package com.example.channels.data.topic.datasource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Topic(
    @SerialName("name")
    val name: String,
)
