package com.example.people.data.datasource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class People(
    @SerialName("user_id")
    val userId: Int,
    @SerialName("email")
    val email: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("is_bot")
    val isBot: Boolean,
    @SerialName("avatar_url")
    val avatarUrl: String?,
)
