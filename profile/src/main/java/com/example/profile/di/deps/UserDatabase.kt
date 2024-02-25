package com.example.profile.di.deps

import com.example.profile.data.datasource.local.UserDao

interface UserDatabase {
    fun userDao(): UserDao
}
