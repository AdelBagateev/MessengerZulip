package com.example.homework2.profile.data.datasource.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class GetUserByIdResponse(
    val user: User
)
