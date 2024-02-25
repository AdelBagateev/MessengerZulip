package com.example.channels.di.bind

import com.example.channels.presentation.mapper.StreamDomainToUiMapper
import com.example.channels.presentation.mapper.StreamDomainToUiMapperImpl
import com.example.channels.presentation.mapper.TopicDomainToUiMapper
import com.example.channels.presentation.mapper.TopicDomainToUiMapperImpl
import dagger.Binds
import dagger.Module

@Module
internal interface ChannelPresenterLayerBindModule {
    @Binds
    fun bindStreamDomainToUiMapperImplToStreamDomainToUiMapper(
        streamDomainToUiMapperImpl: StreamDomainToUiMapperImpl
    ): StreamDomainToUiMapper

    @Binds
    fun bindTopicDomainToUiMapperImplToTopicDomainToUiMapper(
        topicDomainToUiMapperImpl: TopicDomainToUiMapperImpl
    ): TopicDomainToUiMapper
}
