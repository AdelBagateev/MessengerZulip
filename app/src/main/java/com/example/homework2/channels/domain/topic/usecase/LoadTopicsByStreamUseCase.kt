package com.example.homework2.channels.domain.topic.usecase

import com.example.homework2.channels.domain.model.StreamModel
import com.example.homework2.channels.domain.topic.TopicRepository
import javax.inject.Inject

class LoadTopicsByStreamUseCaseImpl @Inject constructor(
    private val topicRepository: TopicRepository
) : LoadTopicsByStreamUseCase {
    override suspend fun invoke(stream: StreamModel) {
        topicRepository.loadTopicByStream(stream)
    }
}

interface LoadTopicsByStreamUseCase {
    suspend operator fun invoke(stream: StreamModel)
}
