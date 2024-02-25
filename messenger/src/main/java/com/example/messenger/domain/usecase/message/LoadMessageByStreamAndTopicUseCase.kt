package com.example.messenger.domain.usecase.message

import com.example.messenger.domain.MessageRepository
import javax.inject.Inject

internal class LoadMessageByStreamAndTopicUseCaseImpl @Inject constructor(
    private val repository: MessageRepository
) : LoadMessageByStreamAndTopicUseCase {
    override suspend fun invoke(streamName: String, topicName: String?, anchor: String) {
        repository.getMessagesByStreamAndTopic(streamName, topicName, anchor)
    }
}

interface LoadMessageByStreamAndTopicUseCase {
    suspend operator fun invoke(
        streamName: String,
        topicName: String?,
        anchor: String
    )
}
