package com.example.homework2.people.di

import com.example.homework2.people.data.PeopleRepositoryImpl
import com.example.homework2.people.domain.*
import com.example.homework2.people.utils.PeopleMapperData
import com.example.homework2.people.utils.PeopleMapperDataImpl
import com.example.homework2.people.utils.PeopleMapperPresentation
import com.example.homework2.people.utils.PeopleMapperPresentationImpl
import dagger.Binds
import dagger.Module

@Module
internal interface PeopleBindModule {
    @Binds
    fun bindGetPeoplePresenceUseCaseImplToGetPeoplePresenceUseCase(
        getPeoplePresenceByIdUseCaseImpl: SubscribeOnPeopleUseCaseImpl
    ): SubscribeOnPeopleUseCase

    @Binds
    fun bindGetPeopleUseCaseImplToGetPresenceUseCase(
        getPeopleByIdUseCaseImpl: LoadPeopleUseCaseImpl
    ): LoadPeopleUseCase

    @Binds
    fun bindPeopleRepositoryImplToPeopleRepository(
        peopleRepositoryImpl: PeopleRepositoryImpl
    ): PeopleRepository

    @Binds
    fun bindPeopleMapperDataImplToPeopleMapperData(
        peopleMapperDataImpl: PeopleMapperDataImpl
    ): PeopleMapperData

    @Binds
    fun bindPeopleMapperPresentationImplToPeopleMapperPresentation(
        peopleMapperPresentationImpl: PeopleMapperPresentationImpl
    ): PeopleMapperPresentation
}
