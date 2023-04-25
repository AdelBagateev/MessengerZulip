package com.example.homework2.profile.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: Int,
    val avatar: String?,
    val name: String,
    val status: String = "active",
) : Parcelable
