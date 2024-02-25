package com.example.people.di.bind

import com.example.people.presentation.mapper.PeopleDomainToUiMapper
import com.example.people.presentation.mapper.PeopleDomainToUiMapperImpl
import dagger.Binds
import dagger.Module

@Module
internal interface PeoplePresenterLayerBindModule {
    @Binds
    fun bindPeopleMapperPresentationImplToPeopleMapperPresentation(
        peopleMapperPresentationImpl: PeopleDomainToUiMapperImpl
    ): PeopleDomainToUiMapper
}
