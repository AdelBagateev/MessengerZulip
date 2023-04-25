package com.example.homework2.people.data.datasource.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class People(
    val user_id: Int,
    val email: String,
    val full_name: String,
    val date_joined: String,
    val is_active: Boolean,
    val is_owner: Boolean,
    val is_admin: Boolean,
    val is_guest: Boolean,
    val is_bot: Boolean,
    val avatar_url: String?,
)
