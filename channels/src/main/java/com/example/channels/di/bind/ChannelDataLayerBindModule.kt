package com.example.channels.di.bind

import com.example.channels.data.stream.StreamsRepositoryImpl
import com.example.channels.data.stream.mapper.*
import com.example.channels.data.topic.TopicRepositoryImpl
import com.example.channels.data.topic.mapper.TopicApiToDbMapper
import com.example.channels.data.topic.mapper.TopicApiToDbMapperImpl
import com.example.channels.data.topic.mapper.TopicDbToDomainMapper
import com.example.channels.data.topic.mapper.TopicDbToDomainMapperImpl
import com.example.channels.domain.stream.StreamsRepository
import com.example.channels.domain.topic.TopicRepository
import dagger.Binds
import dagger.Module

@Module
internal interface ChannelDataLayerBindModule {
    //STREAMS
    @Binds
    fun bindStreamsRepositoryImplToStreamsRepository(
        streamsRepositoryImpl: StreamsRepositoryImpl
    ): StreamsRepository

    @Binds
    fun bindStreamMappersDataImplToStreamMappersData(
        streamMappersDataImpl: SubscribedStreamApiToDbMapperImpl
    ): SubscribedStreamApiToDbMapper

    @Binds
    fun bindStreamApiToDbMapperImplToStreamApiToDbMapper(
        streamApiToDbMapper: StreamApiToDbMapperImpl
    ): StreamApiToDbMapper

    @Binds
    fun bindStreamDbToPresenterMapperImplToStreamDbToPresenterMapper(
        streamDbToPresenterMapper: StreamDbToDomainMapperImpl
    ): StreamDbToDomainMapper

    //TOPICS
    @Binds
    fun bindTopicRepositoryImplTopicRepository(
        topicRepositoryImpl: TopicRepositoryImpl
    ): TopicRepository

    @Binds
    fun bindTopicApiToDbMapperImplToTopicApiToDbMapper(
        topicApiToDbMapper: TopicApiToDbMapperImpl
    ): TopicApiToDbMapper

    @Binds
    fun bindTopicDbToDomainMapperImplToTopicDbToDomainMapper(
        topicDbToDomainMapperImpl: TopicDbToDomainMapperImpl
    ): TopicDbToDomainMapper
}
