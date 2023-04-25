package com.example.homework2.dialog.domain.usecase.message

import com.example.homework2.dialog.domain.MessageRepository
import javax.inject.Inject

class LoadMessageByStreamAndTopicUseCaseImpl @Inject constructor(
    private val repository: MessageRepository
) : LoadMessageByStreamAndTopicUseCase {
    override suspend fun invoke(streamName: String, topicName: String, anchor: String) {
        repository.getMessagesByStreamAndTopic(streamName, topicName, anchor)
    }
}

interface LoadMessageByStreamAndTopicUseCase {
    suspend operator fun invoke(
        streamName: String,
        topicName: String,
        anchor: String
    )
}
