package com.example.homework2.channels.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopicModel(
    val name: String,
    val stream: StreamModel,
) : Parcelable
