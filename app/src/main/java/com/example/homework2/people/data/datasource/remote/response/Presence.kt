package com.example.homework2.people.data.datasource.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class Presence(
    val client: String,
    val status: String,
    val timestamp: Double,
)