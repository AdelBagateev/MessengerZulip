package com.example.channels.domain.topic.usecase

import com.example.channels.domain.model.StreamModel
import com.example.channels.domain.topic.TopicRepository
import javax.inject.Inject

internal class LoadTopicsByStreamUseCaseImpl @Inject constructor(
    private val topicRepository: TopicRepository
) : LoadTopicsByStreamUseCase {
    override suspend fun invoke(stream: StreamModel) {
        topicRepository.loadTopicByStream(stream)
    }
}

interface LoadTopicsByStreamUseCase {
    suspend operator fun invoke(stream: StreamModel)
}
