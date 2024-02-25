package com.example.messenger.di.data

import com.example.messenger.data.datasource.local.MessageDao
import com.example.messenger.di.MessengerScope
import com.example.messenger.di.deps.MessageDatabase
import dagger.Module
import dagger.Provides

@Module
class MessengerDaoModule {
    @MessengerScope
    @Provides
    fun provideMessageDao(db: MessageDatabase): MessageDao =
        db.messageDao()
}
