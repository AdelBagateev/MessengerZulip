package com.example.people.data.datasource.remote

import com.example.people.data.datasource.remote.response.PeoplePresenceResponse
import com.example.people.data.datasource.remote.response.PeopleResponse
import retrofit2.http.GET

internal interface PeopleApi {
    @GET("users")
    suspend fun getPeople(): PeopleResponse

    @GET("realm/presence")
    suspend fun getPeoplePresence(): PeoplePresenceResponse
}
