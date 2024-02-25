package com.example.profile.di.bind

import com.example.profile.domain.usecase.LoadUserByIdUseCase
import com.example.profile.domain.usecase.LoadUserByIdUseCaseImpl
import com.example.profile.domain.usecase.SubscribeOnUserUseCase
import com.example.profile.domain.usecase.SubscribeOnUserUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface ProfileDomainLayerBindModule {
    @Binds
    fun bindGetUserPresenceByIdUseCaseImplToGetUserPresenceByIdUseCase(
        getUserPresenceByIdUseCaseImpl: LoadUserByIdUseCaseImpl
    ): LoadUserByIdUseCase

    @Binds
    fun bindSubscribeOnUserUseCaseImplToSubscribeOnUserUseCase(
        subscribeOnUserUseCaseImpl: SubscribeOnUserUseCaseImpl
    ): SubscribeOnUserUseCase
}
