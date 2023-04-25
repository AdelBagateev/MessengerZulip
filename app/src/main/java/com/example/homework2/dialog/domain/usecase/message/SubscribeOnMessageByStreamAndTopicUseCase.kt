package com.example.homework2.dialog.domain.usecase.message

import com.example.homework2.dialog.domain.MessageRepository
import com.example.homework2.dialog.domain.model.MessageModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SubscribeOnMessageByStreamAndTopicUseCaseImpl @Inject constructor(
    private val messageRepository: MessageRepository
) : SubscribeOnMessageByStreamAndTopicUseCase {
    override operator fun invoke(
        streamName: String,
        topicName: String
    ): Flow<List<MessageModel>> =
        messageRepository.subscribeOnMessagesByStreamAndTopic(streamName, topicName)
}

interface SubscribeOnMessageByStreamAndTopicUseCase {
    operator fun invoke(streamName: String, topicName: String): Flow<List<MessageModel>>
}
