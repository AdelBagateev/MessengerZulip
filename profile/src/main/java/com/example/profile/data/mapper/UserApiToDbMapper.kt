package com.example.profile.data.mapper

import com.example.profile.data.datasource.local.entity.UserDB
import com.example.profile.data.datasource.remote.response.Presence
import com.example.profile.data.datasource.remote.response.User
import javax.inject.Inject

interface UserApiToDbMapper {
    fun toUserDB(map: Map<String, Presence>, user: User): UserDB
}

internal class UserApiToDbMapperImpl @Inject constructor() : UserApiToDbMapper {
    override fun toUserDB(map: Map<String, Presence>, user: User): UserDB {
        val userStatus = toUserStatus(map)
        return UserDB(user.userId, user.avatarUrl, user.fullName, userStatus)
    }

    private fun toUserStatus(map: Map<String, Presence>): String {
        val value = map.values.first()
        return value.status
    }

}

