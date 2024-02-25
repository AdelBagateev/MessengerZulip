package com.example.messenger.data.datasource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Message(
    @SerialName("id")
    val id: Int,
    @SerialName("avatar_url")
    val avatarUrl: String?,
    @SerialName("content")
    val content: String,
    @SerialName("sender_full_name")
    val senderFullName: String,
    @SerialName("sender_id")
    val senderId: Int,
    @SerialName("reactions")
    val reactions: List<Reaction>,
    @SerialName("display_recipient")
    val displayRecipient: String,
    @SerialName("subject")
    val subject: String,
    @SerialName("timestamp")
    val timestamp: Int,
)

@Serializable
class Reaction(
    val emoji_name: String,
    val emoji_code: String,
    val user_id: Int,
)
