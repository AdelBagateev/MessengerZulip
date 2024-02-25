package com.example.profile.data.datasource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UserPresenceResponse(
    @SerialName("presence")
    val presence: Map<String, Presence>
)
