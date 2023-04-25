package com.example.homework2.profile.data.datasource.remote

import com.example.homework2.profile.data.datasource.remote.response.GetUserByIdResponse
import com.example.homework2.profile.data.datasource.remote.response.UserPresenceResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApi {
    @GET("users/{user_id}/presence")
    suspend fun getUserPresenceById(@Path("user_id") id: Int): UserPresenceResponse

    @GET("users/{user_id}")
    suspend fun getUserById(@Path("user_id") id: Int): GetUserByIdResponse
}
