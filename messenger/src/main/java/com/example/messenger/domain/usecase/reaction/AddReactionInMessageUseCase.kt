package com.example.messenger.domain.usecase.reaction

import com.example.messenger.data.datasource.remote.response.UpdateReactionResponse
import com.example.messenger.domain.MessageRepository
import javax.inject.Inject

internal class AddReactionInMessageUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository
) : AddReactionInMessageUseCase {
    override suspend operator fun invoke(
        messageId: Int,
        emojiName: String
    ): UpdateReactionResponse =
        messageRepository.addReactionToMessage(messageId, emojiName)
}

interface AddReactionInMessageUseCase {
    suspend operator fun invoke(messageId: Int, emojiName: String): UpdateReactionResponse
}
