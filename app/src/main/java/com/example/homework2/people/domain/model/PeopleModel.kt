package com.example.homework2.people.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PeopleModel(
    val id: Int,
    val fullName: String,
    val avatarUrl: String?,
    val mail: String,
    val status: String,
    val isBot: Boolean
) : Parcelable
