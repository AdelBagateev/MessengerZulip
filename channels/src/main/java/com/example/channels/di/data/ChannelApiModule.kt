package com.example.channels.di.data

import com.example.channels.data.stream.datasource.remote.StreamApi
import com.example.channels.data.topic.datasource.remote.TopicApi
import com.example.channels.di.ChannelScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ChannelApiModule {
    @ChannelScope
    @Provides
    internal fun provideStreamApi(retrofit: Retrofit): StreamApi {
        return retrofit.create(StreamApi::class.java)
    }

    @ChannelScope
    @Provides
    internal fun provideTopicApi(retrofit: Retrofit): TopicApi {
        return retrofit.create(TopicApi::class.java)
    }
}
