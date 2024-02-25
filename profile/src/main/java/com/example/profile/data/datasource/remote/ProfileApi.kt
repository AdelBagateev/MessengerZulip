package com.example.profile.data.datasource.remote

import com.example.profile.data.datasource.remote.response.GetUserByIdResponse
import com.example.profile.data.datasource.remote.response.UserPresenceResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ProfileApi {
    @GET("users/{user_id}/presence")
    suspend fun getUserPresenceById(@Path("user_id") id: Int): UserPresenceResponse

    @GET("users/{user_id}")
    suspend fun getUserById(@Path("user_id") id: Int): GetUserByIdResponse
}
