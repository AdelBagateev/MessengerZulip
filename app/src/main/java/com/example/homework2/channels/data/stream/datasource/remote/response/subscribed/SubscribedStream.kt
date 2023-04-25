package com.example.homework2.channels.data.stream.datasource.remote.response.subscribed

import kotlinx.serialization.Serializable

@Serializable
data class SubscribedStream(
    val stream_id: Int,
    val name: String,
    val description: String,
    val date_created: Int,
    val invite_only: Boolean,
    val email_address: String,
    val is_muted: Boolean,
    val color: String,
)