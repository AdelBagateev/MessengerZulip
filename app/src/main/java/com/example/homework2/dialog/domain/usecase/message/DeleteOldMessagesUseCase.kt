package com.example.homework2.dialog.domain.usecase.message

import com.example.homework2.dialog.domain.MessageRepository
import javax.inject.Inject

class DeleteOldMessagesUseCaseImpl @Inject constructor(
    private val repository: MessageRepository
) : DeleteOldMessagesUseCase {
    override suspend fun invoke(streamName: String, topicName: String) {
        repository.deleteOldMessages(streamName, topicName)
    }
}

interface DeleteOldMessagesUseCase {
    suspend operator fun invoke(streamName: String, topicName: String)
}
