package com.example.homework2.people.data.datasource.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class PeoplePresenceResponse(
    val server_timestamp: Double,
    val presences: Map<String, Map<String, Presence>>
)
