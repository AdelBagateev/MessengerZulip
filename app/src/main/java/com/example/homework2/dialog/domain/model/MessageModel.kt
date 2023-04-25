package com.example.homework2.dialog.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageModel(
    var id: Int,
    val timestamp: Int,
    val avatarUrl: String?,
    val nameSender: String?,
    val content: String,
    val mediaContent: String ?= null,
    var reactions: MutableList<ReactionModel>,
    val isMy: Boolean,
) : Parcelable
