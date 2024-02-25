package com.example.profile.data.mapper

import com.example.profile.data.datasource.local.entity.UserDB
import com.example.profile.domain.model.UserModel
import javax.inject.Inject

interface UserDbToDomainMapper {
    fun toUserModel(user: UserDB): UserModel
}

internal class UserDbToDomainMapperImpl @Inject constructor() : UserDbToDomainMapper {
    override fun toUserModel(user: UserDB): UserModel {
        return UserModel(
            user.id,
            user.avatar,
            user.name,
            user.status
        )
    }
}

