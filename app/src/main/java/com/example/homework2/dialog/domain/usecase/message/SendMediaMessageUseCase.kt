package com.example.homework2.dialog.domain.usecase.message

import com.example.homework2.dialog.domain.MessageRepository
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
