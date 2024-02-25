package com.example.profile.di.data

import com.example.profile.data.datasource.local.UserDao
import com.example.profile.di.ProfileScope
import com.example.profile.di.deps.UserDatabase
import dagger.Module
import dagger.Provides

@Module
class ProfileDaoModule {
    @ProfileScope
    @Provides
    fun provideUserDao(db: UserDatabase): UserDao =
        db.userDao()
}

