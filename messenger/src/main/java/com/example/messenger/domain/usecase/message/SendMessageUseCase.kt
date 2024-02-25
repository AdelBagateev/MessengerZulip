package com.example.messenger.domain.usecase.message

import com.example.messenger.data.datasource.remote.response.SendMessageResponse
import com.example.messenger.domain.MessageRepository
import javax.inject.Inject

internal class SendMessageUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository
) : SendMessageUseCase {
    override suspend operator fun invoke(
        content: String,
        streamName: String,
        topicName: String
    ): SendMessageResponse =
        messageRepository.sendMessage(content, streamName, topicName)
}

interface SendMessageUseCase {
    suspend operator fun invoke(
        content: String,
        streamName: String,
        topicName: String
    ): SendMessageResponse
}
