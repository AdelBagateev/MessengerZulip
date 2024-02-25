package com.example.people.data.datasource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PeoplePresenceResponse(
    @SerialName("server_timestamp")
    val serverTimestamp: Double,
    @SerialName("presences")
    val presences: Map<String, Map<String, Presence>>
)
