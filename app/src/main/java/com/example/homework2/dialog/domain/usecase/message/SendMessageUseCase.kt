package com.example.homework2.dialog.domain.usecase.message

import com.example.homework2.dialog.data.datasource.remote.response.SendMessageResponse
import com.example.homework2.dialog.domain.MessageRepository
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
