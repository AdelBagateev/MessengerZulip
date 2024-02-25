package com.example.messenger.domain.usecase.reaction

import com.example.messenger.data.datasource.remote.response.UpdateReactionResponse
import com.example.messenger.domain.MessageRepository
import javax.inject.Inject

internal class RemoveReactionInMessageUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository
) : RemoveReactionInMessageUseCase {
    override suspend operator fun invoke(
        messageId: Int,
        emojiName: String
    ): UpdateReactionResponse =
        messageRepository.removeReactionInMessage(messageId, emojiName)
}

interface RemoveReactionInMessageUseCase {
    suspend operator fun invoke(messageId: Int, emojiName: String): UpdateReactionResponse
}
