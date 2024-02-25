package com.example.profile.data

import com.example.common.asyncAwaitAndTransform
import com.example.profile.data.datasource.local.UserDao
import com.example.profile.data.datasource.local.entity.UserDB
import com.example.profile.data.datasource.remote.ProfileApi
import com.example.profile.data.mapper.UserApiToDbMapper
import com.example.profile.data.mapper.UserDbToDomainMapper
import com.example.profile.domain.ProfileRepository
import com.example.profile.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

internal class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi,
    private val local: UserDao,
    private val userApiToDbMapper: UserApiToDbMapper,
    private val userDbToDomainMapper: UserDbToDomainMapper
) : ProfileRepository {
    override suspend fun getActualDataOfUser(id: Int) {
        val user = getUserFromNetwork(id)
        saveUserInDb(user)
    }

    override fun subscribeOnUser(id: Int): Flow<UserModel> {
        return local.getUserById(id)
            .filterNotNull()
            .mapNotNull { userDbToDomainMapper.toUserModel(it) }
    }

    private suspend fun getUserFromNetwork(id: Int): UserDB {
        val userDb =
            asyncAwaitAndTransform(
                { api.getUserPresenceById(id).presence },
                { api.getUserById(id).user }
            ) { presence, user ->
                userApiToDbMapper.toUserDB(presence, user)
            }
        return userDb
    }

    private suspend fun saveUserInDb(user: UserDB) {
        local.saveUser(user)
    }
}
