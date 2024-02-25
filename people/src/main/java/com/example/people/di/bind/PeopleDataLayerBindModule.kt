package com.example.people.di.bind

import com.example.people.data.PeopleRepositoryImpl
import com.example.people.data.mapper.PeopleApiToDbMapper
import com.example.people.data.mapper.PeopleApiToDbMapperImpl
import com.example.people.data.mapper.PeopleDbToDomainMapper
import com.example.people.data.mapper.PeopleDbToDomainMapperImpl
import com.example.people.domain.PeopleRepository
import dagger.Binds
import dagger.Module

@Module
internal interface PeopleDataLayerBindModule {
    @Binds
    fun bindPeopleRepositoryImplToPeopleRepository(
        peopleRepositoryImpl: PeopleRepositoryImpl
    ): PeopleRepository

    @Binds
    fun bindPeopleApiToDbMapperImplToPeopleApiToDbMapper(
        peopleApiToDbMapperImpl: PeopleApiToDbMapperImpl
    ): PeopleApiToDbMapper

    @Binds
    fun bindPeopleDbToPresenterImplToPeopleDbToPresenter(
        peopleDbToPresenterImpl: PeopleDbToDomainMapperImpl
    ): PeopleDbToDomainMapper
}
