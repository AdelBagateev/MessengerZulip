package com.example.profile.data.datasource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GetUserByIdResponse(
    @SerialName("user")
    val user: User
)
