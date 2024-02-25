package com.example.messenger.domain.usecase.message

import com.example.messenger.domain.MessageRepository
import javax.inject.Inject

internal class DeleteMessageByIdUseCaseImpl @Inject constructor(
    private val repository: MessageRepository
) : DeleteMessageByIdUseCase {
    override suspend fun invoke(messageId: Int) {
        repository.deleteMessageById(messageId)
    }
}

interface DeleteMessageByIdUseCase {
    suspend operator fun invoke(messageId: Int)
}
