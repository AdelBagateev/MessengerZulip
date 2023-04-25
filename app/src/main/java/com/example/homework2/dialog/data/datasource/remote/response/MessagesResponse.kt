package com.example.homework2.dialog.data.datasource.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class MessagesResponse(
    val messages: List<Message>
)


