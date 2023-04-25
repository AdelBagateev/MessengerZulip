package com.example.homework2.dialog.data.datasource.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: Int,
    val avatar_url: String?,
    val content: String,
    val is_me_message: Boolean,
    val sender_email: String,
    val sender_full_name: String,
    val sender_id: Int,
    val reactions: List<Reaction>,
    val stream_id: Int,
    val display_recipient : String,
    val subject: String,
    val timestamp: Int,
)

@Serializable
data class Reaction(
    val emoji_name: String,
    val emoji_code: String,
    val reaction_type: String,
    val user_id: Int,
)
