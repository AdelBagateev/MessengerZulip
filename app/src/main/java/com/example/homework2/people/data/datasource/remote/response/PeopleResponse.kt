package com.example.homework2.people.data.datasource.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class PeopleResponse(
    val members: List<People>
)
