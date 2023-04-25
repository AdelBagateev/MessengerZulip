package com.example.homework2.dialog.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReactionModel(
    val emojiCode: String,
    val userId: Int,
    val count: Int,
    val isSelected: Boolean = false,
    val emojiName: String
) : Parcelable