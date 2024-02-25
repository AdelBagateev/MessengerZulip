package com.example.messenger.domain.usecase.message

import com.example.messenger.domain.MessageRepository
import javax.inject.Inject

internal class UpdateMessageUseCaseImpl @Inject constructor(
    private val repository: MessageRepository
) : UpdateMessageUseCase {
    override suspend fun invoke(messageId: Int, content: String, topicName: String) {
        repository.updateMessage(messageId, content, topicName)
    }
}

interface UpdateMessageUseCase {
    suspend operator fun invoke(messageId: Int, content: String, topicName: String)
}
