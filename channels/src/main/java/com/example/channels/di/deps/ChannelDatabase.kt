package com.example.channels.di.deps

import com.example.channels.data.stream.datasource.local.StreamDao
import com.example.channels.data.topic.datasource.local.TopicDao

interface ChannelDatabase {
    fun streamDao(): StreamDao
    fun topicDao(): TopicDao
}
