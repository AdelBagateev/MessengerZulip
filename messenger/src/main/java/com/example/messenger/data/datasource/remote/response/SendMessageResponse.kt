package com.example.messenger.data.datasource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SendMessageResponse(
    @SerialName("id")
    val id: Int,
)
