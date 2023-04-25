package com.example.homework2.profile.domain

import com.example.homework2.profile.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getActualDataOfUser(id: Int)

    fun subscribeOnUser(id: Int) : Flow<UserModel>
}
