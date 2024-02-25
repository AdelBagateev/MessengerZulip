package com.example.channels.di.data

import com.example.channels.data.stream.datasource.local.StreamDao
import com.example.channels.data.topic.datasource.local.TopicDao
import com.example.channels.di.ChannelScope
import com.example.channels.di.deps.ChannelDatabase
import dagger.Module
import dagger.Provides

@Module
class ChannelDaoModule {
    @ChannelScope
    @Provides
    fun provideStreamDao(db: ChannelDatabase): StreamDao =
        db.streamDao()

    @ChannelScope
    @Provides
    fun provideTopicDao(db: ChannelDatabase): TopicDao =
        db.topicDao()
}
