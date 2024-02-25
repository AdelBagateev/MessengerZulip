package com.example.channels.di.bind

import com.example.channels.domain.stream.usecase.*
import com.example.channels.domain.topic.usecase.LoadTopicsByStreamUseCase
import com.example.channels.domain.topic.usecase.LoadTopicsByStreamUseCaseImpl
import com.example.channels.domain.topic.usecase.SubscribeOnTopicsByStreamUseCase
import com.example.channels.domain.topic.usecase.SubscribeOnTopicsByStreamUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
internal interface ChannelDomainLayerBindModule {
    @Binds
    fun bindGetAllStreamsUseCaseImplToGetAllStreamsUseCase(
        getAllStreamsUseCaseImpl: SubscribeOnStreamUseCaseImpl
    ): SubscribeOnStreamUseCase

    @Binds
    fun bindCreateNewStreamUseCaseImplToCreateNewStreamUseCase(
        createNewStreamUseCaseImpl: CreateNewStreamUseCaseImpl
    ): CreateNewStreamUseCase

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
}
