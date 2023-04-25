package com.example.homework2.profile.utils

import com.example.homework2.profile.data.datasource.local.entity.UserDB
import com.example.homework2.profile.data.datasource.remote.response.Presence
import com.example.homework2.profile.data.datasource.remote.response.User
import com.example.homework2.profile.domain.model.UserModel
import javax.inject.Inject

interface UserMapperData {
    fun toUserDB(map : Map<String, Presence>, user: User): UserDB
    fun toUserModel(user: UserDB) : UserModel
}

class UserMapperDataImpl @Inject constructor() : UserMapperData {
    override fun toUserDB(map: Map<String, Presence>, user: User): UserDB {
        val userStatus = toUserStatus(map)
        return UserDB(user.user_id, user.avatar_url, user.full_name, userStatus)
    }
    override fun toUserModel(user: UserDB): UserModel {
        return UserModel(
            user.id,
            user.avatar,
            user.name,
            user.status
        )
    }
    private fun toUserStatus(map: Map<String, Presence>): String {
        val value = map.values.first()
        return value.status
    }

}

