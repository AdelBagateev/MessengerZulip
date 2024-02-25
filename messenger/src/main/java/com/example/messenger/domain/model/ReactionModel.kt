package com.example.messenger.domain.model


data class ReactionModel(
    val emojiCode: String,
    val userId: Int,
    val count: Int,
    val isSelected: Boolean = false,
    val emojiName: String
)
