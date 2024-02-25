package com.example.messenger.domain.usecase.message

import com.example.messenger.domain.MessageRepository
import javax.inject.Inject

internal class DeleteOldMessagesUseCaseImpl @Inject constructor(
    private val repository: MessageRepository
) : DeleteOldMessagesUseCase {
    override suspend fun invoke(streamName: String) {
        repository.deleteOldMessagesInDB(streamName)
    }
}

interface DeleteOldMessagesUseCase {
    suspend operator fun invoke(streamName: String)
}
