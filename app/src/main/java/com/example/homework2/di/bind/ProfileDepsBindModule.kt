package com.example.homework2.di.bind

import com.example.homework2.AppDatabase
import com.example.homework2.di.navigation.wrappers.ProfileRouterWrapper
import com.example.profile.di.deps.ProfileRouter
import com.example.profile.di.deps.UserDatabase
import dagger.Binds
import dagger.Module

@Module
interface ProfileDepsBindModule {
    @Binds
    fun bindProfileRouterHolderToProfileRouter(
        profileRouterWrapper: ProfileRouterWrapper
    ): ProfileRouter

    @Binds
    fun bindAppDatabaseToUserDatabase(
        appDatabase: AppDatabase
    ): UserDatabase
}
