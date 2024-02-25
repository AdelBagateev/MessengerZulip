package com.example.people.domain.model


data class PeopleModel(
    val id: Int,
    val fullName: String,
    val avatarUrl: String?,
    val mail: String,
    val status: String,
    val isBot: Boolean
)
