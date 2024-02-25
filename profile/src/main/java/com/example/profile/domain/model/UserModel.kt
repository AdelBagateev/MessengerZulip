package com.example.profile.domain.model


data class UserModel(
    val id: Int,
    val avatar: String?,
    val name: String,
    val status: String = "active",
)
