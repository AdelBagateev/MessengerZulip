package com.example.homework2.global_di.apis

import com.example.homework2.channels.data.stream.datasource.remote.StreamApi
import com.example.homework2.channels.data.topic.datasource.remote.TopicApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ChannelApiModule {
    @Singleton
    @Provides
    fun provideStreamApi(retrofit: Retrofit): StreamApi {
        return retrofit.create(StreamApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTopicApi(retrofit: Retrofit): TopicApi {
        return retrofit.create(TopicApi::class.java)
    }

}
