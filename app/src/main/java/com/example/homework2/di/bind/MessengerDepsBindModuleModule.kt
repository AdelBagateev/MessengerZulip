package com.example.homework2.di.bind

import com.example.homework2.AppDatabase
import com.example.homework2.di.navigation.wrappers.MessengerRouterWrapper
import com.example.messenger.di.deps.MessageDatabase
import com.example.messenger.di.deps.MessengerRouter
import dagger.Binds
import dagger.Module

@Module
interface MessengerDepsBindModuleModule {
    @Binds
    fun bindMessengerRouterHolderToMessengerRouter(
        messengerRouterWrapper: MessengerRouterWrapper
    ): MessengerRouter

    @Binds
    fun bindAppDatabaseToMessageDatabase(
        appDatabase: AppDatabase
    ): MessageDatabase
}
