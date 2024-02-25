package com.example.homework2.di.db

import android.content.Context
import androidx.room.Room
import com.example.homework2.AppDatabase
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
            AppDatabase.DB_NAME
        ).build()
}
