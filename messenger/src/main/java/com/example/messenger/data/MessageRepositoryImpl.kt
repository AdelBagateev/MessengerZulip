package com.example.messenger.data

import com.example.messenger.data.datasource.local.MessageDao
import com.example.messenger.data.datasource.local.entity.MessageWithReaction
import com.example.messenger.data.datasource.remote.MessageApiClient
import com.example.messenger.data.datasource.remote.response.Message
import com.example.messenger.data.datasource.remote.response.SendMessageResponse
import com.example.messenger.data.datasource.remote.response.UpdateReactionResponse
import com.example.messenger.data.mapper.MessageApiToDbMapper
import com.example.messenger.data.mapper.MessageDbToDomainMapper
import com.example.messenger.domain.MessageRepository
import com.example.messenger.domain.model.MessageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import java.io.File
import javax.inject.Inject


internal class MessageRepositoryImpl @Inject constructor(
    private val messageApiClient: MessageApiClient,
    private val local: MessageDao,
    private val messageApiToDbMapper: MessageApiToDbMapper,
    private val messageDbToDomainMapper: MessageDbToDomainMapper,
) : MessageRepository {
    override suspend fun getMessagesByStreamAndTopic(
        streamName: String,
        topicName: String?,
        anchor: String
    ) {
        val messages = getMessagesByAnchor(streamName, topicName, anchor)
        saveMessagesInDb(messages)
    }

    override suspend fun deleteOldMessagesInDB(streamName: String) {
        local.deleteOldMessages(streamName)
    }

    override fun subscribeOnMessagesByStreamAndTopic(
        streamName: String,
        topicName: String?,
    ): Flow<List<MessageModel>> {
        val messagesFlow = if (topicName == null) {
            local.getMessagesByStream(streamName)
        } else {
            local.getMessagesByStreamAndTopic(streamName, topicName)
        }
        return messagesFlow
            .filter { it.isNotEmpty() }
            .mapNotNull { messageDbToDomainMapper.toMessageModel(it) }
    }

    override suspend fun sendMessage(
        content: String,
        streamName: String,
        topicName: String
    ): SendMessageResponse {
        val response = messageApiClient.sendMessage(content, streamName, topicName)
        val message = getNewestMessagesByTopicAndStream(streamName, topicName)
        saveMessagesInDb(message)
        getNewestMessagesByTopicAndStream(streamName, topicName)
        return response
    }

    override suspend fun sendMediaMessage(
        file: File,
        streamName: String,
        topicName: String
    ) {
        val response = messageApiClient.uploadFile(file)
        val urlFromNetwork = response.uri
        val content = "[${file.name}](${urlFromNetwork})"
        sendMessage(content, streamName, topicName)
    }

    override suspend fun addReactionToMessage(
        messageId: Int,
        emojiName: String
    ): UpdateReactionResponse {
        val response = messageApiClient.addReactionInMessage(messageId, emojiName)
        val updatedMsg = getUpdatedMessage(messageId)
        saveMessageInDB(updatedMsg)
        return response
    }

    override suspend fun removeReactionInMessage(
        messageId: Int,
        emojiName: String
    ): UpdateReactionResponse {
        val response = messageApiClient.removeReactionInMessage(messageId, emojiName)
        val updatedMsg = getUpdatedMessage(messageId)
        saveMessageInDB(updatedMsg)
        return response
    }

    override suspend fun updateMessage(messageId: Int, content: String, topicName: String) {
        messageApiClient.updateMessage(messageId, content, topicName)
        val updatedMessage = getUpdatedMessage(messageId)
        saveMessageInDB(updatedMessage)
    }

    override suspend fun deleteMessageById(messageId: Int) {
        messageApiClient.deleteMessage(messageId)
        local.deleteMessageById(messageId)
    }

    private suspend fun getMessagesByAnchor(
        streamName: String,
        topicName: String?,
        anchor: String
    ): List<MessageWithReaction> {
        val response = messageApiClient.getMessagesByStreamAndTopic(streamName, topicName, anchor)
        return messageApiToDbMapper.toMessageWithReactions(response.messages)
    }

    private suspend fun getUpdatedMessage(messageId: Int): Message {
        val messageResponse = messageApiClient.getMessageById(messageId.toString())
        return messageResponse.messages.first()
    }

    private suspend fun getNewestMessagesByTopicAndStream(
        streamName: String,
        topicName: String
    ): List<MessageWithReaction> {
        val response = messageApiClient.getMessagesByStreamAndTopic(streamName, topicName, "newest")
        val messages = response.messages
        return messageApiToDbMapper.toMessageWithReactions(messages)
    }

    private suspend fun saveMessagesInDb(messages: List<MessageWithReaction>) {
        local.saveMessagesWithReaction(messages)
    }

    private suspend fun saveMessageInDB(message: Message) {
        val messages = listOf(message)
        val messagesWithReactions = messageApiToDbMapper.toMessageWithReactions(messages)

        local.saveMessagesWithReaction(messagesWithReactions)
    }
}
