package com.example.homework2.channels.data.stream.datasource.remote.response.all

import kotlinx.serialization.Serializable

@Serializable
data class Stream(
    val stream_id: Int,
    val name: String,
    val description: String,
    val date_created: Int,
    val invite_only: Boolean,
)
