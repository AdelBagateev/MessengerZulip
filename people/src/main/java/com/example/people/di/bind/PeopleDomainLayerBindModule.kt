package com.example.people.di.bind

import com.example.people.domain.usecase.LoadPeopleUseCase
import com.example.people.domain.usecase.LoadPeopleUseCaseImpl
import com.example.people.domain.usecase.SubscribeOnPeopleUseCase
import com.example.people.domain.usecase.SubscribeOnPeopleUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface PeopleDomainLayerBindModule {
    @Binds
    fun bindGetPeoplePresenceUseCaseImplToGetPeoplePresenceUseCase(
        getPeoplePresenceByIdUseCaseImpl: SubscribeOnPeopleUseCaseImpl
    ): SubscribeOnPeopleUseCase

    @Binds
    fun bindGetPeopleUseCaseImplToGetPresenceUseCase(
        getPeopleByIdUseCaseImpl: LoadPeopleUseCaseImpl
    ): LoadPeopleUseCase
} 
