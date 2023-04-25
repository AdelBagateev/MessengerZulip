package com.example.homework2.profile.di

import com.example.homework2.profile.data.ProfileRepositoryImpl
import com.example.homework2.profile.domain.*
import com.example.homework2.profile.utils.UserMapperData
import com.example.homework2.profile.utils.UserMapperDataImpl
import dagger.Binds
import dagger.Module

@Module
internal interface ProfileBindModule {
    @Binds
    fun bindGetUserPresenceByIdUseCaseImplToGetUserPresenceByIdUseCase(
        getUserPresenceByIdUseCaseImpl: LoadUserByIdUseCaseImpl
    ): LoadUserByIdUseCase

    @Binds
    fun bindSubscribeOnUserUseCaseImplToSubscribeOnUserUseCase(
        subscribeOnUserUseCaseImpl: SubscribeOnUserUseCaseImpl
    ): SubscribeOnUserUseCase

    @Binds
    fun bindProfileRepositoryImplToProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    fun bindUserMapperDataImplToUserMapperData(
        userMapperDataImpl: UserMapperDataImpl
    ): UserMapperData

}
