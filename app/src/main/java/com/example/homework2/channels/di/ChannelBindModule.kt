package com.example.homework2.channels.di

import com.example.homework2.channels.data.stream.StreamsRepositoryImpl
import com.example.homework2.channels.data.topic.TopicRepositoryImpl
import com.example.homework2.channels.domain.stream.StreamsRepository
import com.example.homework2.channels.domain.stream.usecase.LoadStreamsUseCase
import com.example.homework2.channels.domain.stream.usecase.LoadStreamsUseCaseImpl
import com.example.homework2.channels.domain.stream.usecase.SubscribeOnStreamUseCase
import com.example.homework2.channels.domain.stream.usecase.SubscribeOnStreamUseCaseImpl
import com.example.homework2.channels.domain.topic.TopicRepository
import com.example.homework2.channels.domain.topic.usecase.LoadTopicsByStreamUseCase
import com.example.homework2.channels.domain.topic.usecase.LoadTopicsByStreamUseCaseImpl
import com.example.homework2.channels.domain.topic.usecase.SubscribeOnTopicsByStreamUseCase
import com.example.homework2.channels.domain.topic.usecase.SubscribeOnTopicsByStreamUseCaseImpl
import com.example.homework2.channels.utils.*
import dagger.Binds
import dagger.Module

@Module
internal interface ChannelBindModule {
    @Binds
    fun bindGetAllStreamsUseCaseImplToGetAllStreamsUseCase(
        getAllStreamsUseCaseImpl: SubscribeOnStreamUseCaseImpl
    ): SubscribeOnStreamUseCase

    @Binds
    fun bindGetSubscribedStreamsUseCaseImplToGetSubscribedStreamsUseCase(
        getSubscribedStreamsUseCaseImpl: LoadStreamsUseCaseImpl
    ): LoadStreamsUseCase

    @Binds
    fun bindGetTopicByStreamUseCaseImplToGetTopicByStreamUseCase(
        getTopicByStreamUseCaseImpl: SubscribeOnTopicsByStreamUseCaseImpl
    ): SubscribeOnTopicsByStreamUseCase

    @Binds
    fun bindLoadTopicsByIdUseCaseImplToLoadTopicsByIdUseCase(
        loadTopicsByIdUseCaseImpl: LoadTopicsByStreamUseCaseImpl
    ): LoadTopicsByStreamUseCase

    @Binds
    fun bindStreamsRepositoryImplToStreamsRepository(
        streamsRepositoryImpl: StreamsRepositoryImpl
    ): StreamsRepository

    @Binds
    fun bindTopicRepositoryImplTopicRepository(
        topicRepositoryImpl: TopicRepositoryImpl
    ): TopicRepository

    @Binds
    fun bindStreamMappersDataImplToStreamMappersData(
        streamMappersDataImpl : StreamMappersDataImpl
    ) : StreamMappersData

    @Binds
    fun bindTopicMapperDataImplToTopicMapperData(
        topicMapperDataImpl : TopicMapperDataImpl
    ) : TopicMapperData

    @Binds
    fun bindChannelMapperPresenterImplToChannelMapperPresenter(
        channelMapperPresenter : ChannelMapperPresenterImpl
    ) : ChannelMapperPresenter

}
