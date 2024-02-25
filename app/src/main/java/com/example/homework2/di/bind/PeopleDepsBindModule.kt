package com.example.homework2.di.bind

import com.example.homework2.AppDatabase
import com.example.homework2.di.navigation.wrappers.PeopleRouterWrapper
import com.example.people.di.deps.PeopleDatabase
import com.example.people.di.deps.PeopleRouter
import dagger.Binds
import dagger.Module

@Module
interface PeopleDepsBindModule {
    @Binds
    fun bindPeopleRouterHolderToPeopleRouter(
        peopleRouterWrapper: PeopleRouterWrapper
    ): PeopleRouter

    @Binds
    fun bindAppDatabaseToPeopleDatabase(
        appDatabase: AppDatabase
    ): PeopleDatabase
}
