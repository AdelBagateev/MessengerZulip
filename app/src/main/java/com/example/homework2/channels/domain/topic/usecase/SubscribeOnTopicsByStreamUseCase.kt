package com.example.homework2.channels.domain.topic.usecase

import com.example.homework2.channels.domain.model.StreamModel
import com.example.homework2.channels.domain.model.TopicModel
import com.example.homework2.channels.domain.topic.TopicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SubscribeOnTopicsByStreamUseCaseImpl @Inject constructor(
    private val topicRepository: TopicRepository
) : SubscribeOnTopicsByStreamUseCase {
    override  operator fun invoke(stream: StreamModel): Flow<List<TopicModel>> =
        topicRepository.subscribeOnTopicsByStream(stream)
}

interface SubscribeOnTopicsByStreamUseCase {
     operator fun invoke(stream: StreamModel): Flow<List<TopicModel>>
}
