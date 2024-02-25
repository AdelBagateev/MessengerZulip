package com.example.profile.domain

import com.example.profile.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getActualDataOfUser(id: Int)
    fun subscribeOnUser(id: Int): Flow<UserModel>
}
