package com.example.people.data.datasource.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PeopleResponse(
    @SerialName("members")
    val members: List<People>
)
