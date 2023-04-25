package com.example.homework2.dialog.domain.usecase.reaction

import com.example.homework2.dialog.data.datasource.remote.response.UpdateReactionResponse
import com.example.homework2.dialog.domain.MessageRepository
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
