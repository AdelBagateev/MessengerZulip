package com.example.homework2.channels.data.topic.datasource.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val max_id: Int,
    val name: String,
)
