package com.example.messenger.di.deps

import com.example.messenger.data.datasource.local.MessageDao

interface MessageDatabase {
    fun messageDao(): MessageDao
}
