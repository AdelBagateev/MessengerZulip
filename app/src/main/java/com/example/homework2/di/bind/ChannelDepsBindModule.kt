package com.example.homework2.di.bind

import com.example.channels.di.deps.ChannelDatabase
import com.example.channels.di.deps.ChannelRouter
import com.example.homework2.AppDatabase
import com.example.homework2.di.navigation.wrappers.ChannelRouterWrapper
import dagger.Binds
import dagger.Module

@Module
interface ChannelDepsBindModule {
    @Binds
    fun bindChannelRouterHolderToChannelRouter(
        channelRouterWrapper: ChannelRouterWrapper
    ): ChannelRouter

    @Binds
    fun bindAppDatabaseToChannelDatabase(
        appDatabase: AppDatabase
    ): ChannelDatabase
}
