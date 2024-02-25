package com.example.profile.di.bind

import com.example.profile.data.ProfileRepositoryImpl
import com.example.profile.data.mapper.UserApiToDbMapper
import com.example.profile.data.mapper.UserApiToDbMapperImpl
import com.example.profile.data.mapper.UserDbToDomainMapper
import com.example.profile.data.mapper.UserDbToDomainMapperImpl
import com.example.profile.domain.ProfileRepository
import dagger.Binds
import dagger.Module

@Module
internal interface ProfileDataLayerBindModule {
    @Binds
    fun bindProfileRepositoryImplToProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    fun bindUserApiToDbMapperImplToUserApiToDbMapper(
        userMapperDataImpl: UserApiToDbMapperImpl
    ): UserApiToDbMapper

    @Binds
    fun bindUserDbToPresenterMapperImplToUserDbToPresenterMapper(
        userDbToPresenterMapper: UserDbToDomainMapperImpl
    ): UserDbToDomainMapper
}
