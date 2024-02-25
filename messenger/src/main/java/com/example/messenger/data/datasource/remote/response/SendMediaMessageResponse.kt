package com.example.messenger.data.datasource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SendMediaMessageResponse(
    @SerialName("uri")
    val uri: String,
)
