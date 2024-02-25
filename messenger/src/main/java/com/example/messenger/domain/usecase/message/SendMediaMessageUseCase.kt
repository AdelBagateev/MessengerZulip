package com.example.messenger.domain.usecase.message

import com.example.messenger.domain.MessageRepository
import java.io.File
import javax.inject.Inject

internal class SendMediaMessageUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository
) : SendMediaMessageUseCase {
    override suspend operator fun invoke(
        file: File,
        streamName: String,
        topicName: String
    ) {
        messageRepository.sendMediaMessage(file, streamName, topicName)
    }
}

interface SendMediaMessageUseCase {
    suspend operator fun invoke(
        file: File,
        streamName: String,
        topicName: String
    )
}
