package com.example.homework2.profile.data

import com.example.homework2.common.asyncAwait
import com.example.homework2.profile.data.datasource.local.UserDao
import com.example.homework2.profile.data.datasource.local.entity.UserDB
import com.example.homework2.profile.data.datasource.remote.ProfileApi
import com.example.homework2.profile.domain.ProfileRepository
import com.example.homework2.profile.domain.model.UserModel
import com.example.homework2.profile.utils.UserMapperData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

internal class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi,
    private val local: UserDao,
    private val mapper : UserMapperData
) : ProfileRepository {
    override suspend fun getActualDataOfUser(id: Int)  {
        val user = getUserFromNetwork(id)
        saveUserInDb(user)
    }

    override fun subscribeOnUser(id: Int): Flow<UserModel> {
        return local.getUserById(id)
            .filterNotNull()
            .mapNotNull { mapper.toUserModel(it) }
    }

    private suspend fun getUserFromNetwork(id: Int) : UserDB{
        val userDb =
             asyncAwait(
                { api.getUserPresenceById(id).presence },
                { api.getUserById(id).user }
            ) {presence, user ->
                mapper.toUserDB(presence, user)
            }
        return userDb
    }

    private fun saveUserInDb(user: UserDB) {
        local.saveUser(user)
    }
}
