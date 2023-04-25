package com.example.homework2.channels.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StreamModel(
    val id: Int,
    val name: String,
    val isChosen: Boolean = false
) : Parcelable
