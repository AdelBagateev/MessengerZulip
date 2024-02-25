package com.example.messenger.domain.model


data class MessageModel(
    val id: Int,
    val timestamp: Int,
    val avatarUrl: String?,
    val nameSender: String?,
    val content: String,
    val mediaContent: String? = null,
    val reactions: MutableList<ReactionModel>,
    val isMy: Boolean,
    val streamName: String,
    val topicName: String
)
