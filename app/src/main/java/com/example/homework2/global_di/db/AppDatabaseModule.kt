package com.example.homework2.global_di.db

import android.content.Context
import androidx.room.Room
import com.example.homework2.AppDatabase
import com.example.homework2.channels.data.stream.datasource.local.StreamDao
import com.example.homework2.channels.data.topic.datasource.local.TopicDao
import com.example.homework2.dialog.data.datasource.local.MessageDao
import com.example.homework2.people.data.datasource.local.PeopleDao
import com.example.homework2.profile.data.datasource.local.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(applicationContext: Context): AppDatabase =
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "AppDataBase"
        ).build()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao =
        db.userDao()

    @Provides
    fun providePeopleDao(db: AppDatabase): PeopleDao =
        db.peopleDao()

    @Provides
    fun provideStreamDao(db: AppDatabase): StreamDao =
        db.streamDao()

    @Provides
    fun provideTopicDao(db: AppDatabase) : TopicDao =
        db.topicDao()

    @Provides
    fun provideMessageDao(db: AppDatabase) : MessageDao =
        db.messageDao()
}
