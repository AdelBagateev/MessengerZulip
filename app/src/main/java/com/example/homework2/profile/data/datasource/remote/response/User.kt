package com.example.homework2.profile.data.datasource.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val user_id: Int,
    val email: String,
    val full_name: String,
    val is_active: Boolean,
    val role: Int,
    val timezone: String,
    val avatar_url: String?,
)
