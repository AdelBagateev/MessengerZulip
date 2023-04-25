package com.example.homework2.dialog.domain.usecase.reaction

import com.example.homework2.dialog.data.datasource.remote.response.UpdateReactionResponse
import com.example.homework2.dialog.domain.MessageRepository
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
