package com.example.homework2.dialog.data

import com.example.homework2.dialog.data.datasource.local.MessageDao
import com.example.homework2.dialog.data.datasource.local.entity.MessageWithReaction
import com.example.homework2.dialog.data.datasource.remote.MessageApiClient
import com.example.homework2.dialog.data.datasource.remote.response.SendMessageResponse
import com.example.homework2.dialog.data.datasource.remote.response.UpdateReactionResponse
import com.example.homework2.dialog.domain.MessageRepository
import com.example.homework2.dialog.domain.model.MessageModel
import com.example.homework2.dialog.utils.DialogMapperData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import java.io.File
import javax.inject.Inject


internal class MessageRepositoryImpl @Inject constructor(
    private val messageApiClient: MessageApiClient,
    private val local: MessageDao,
    private val mapper : DialogMapperData
) : MessageRepository {
    override suspend fun getMessagesByStreamAndTopic(
        streamName: String,
        topicName: String,
        anchor : String
    ) {
        val messages = getMessagesFromNetwork(streamName, topicName, anchor)
        saveMessagesInDb(messages)
    }

    override suspend fun deleteOldMessages(streamName: String, topicName: String) {
        local.deleteOldMessages(streamName, topicName)
    }

    override fun subscribeOnMessagesByStreamAndTopic(
        streamName: String,
        topicName: String,
    ): Flow<List<MessageModel>> {
        return  local.getMessages(streamName, topicName)
            .filter { it.isNotEmpty() }
            .mapNotNull { mapper.toMessageModel(it)}
    }

    override suspend fun sendMessage(
        content: String,
        streamName: String,
        topicName: String
    ): SendMessageResponse {
        val response = messageApiClient.sendMessage(content, streamName, topicName)
        saveSentMessageInDB(response.id)

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
        saveSentMessageInDB(messageId)
        return response
    }
    private suspend fun getMessagesFromNetwork(
        streamName: String,
        topicName: String,
        anchor: String
    ) :List<MessageWithReaction> {
        val response = messageApiClient.getMessagesByStreamAndTopic(streamName, topicName, anchor)
        return mapper.toMessageWithReactions(response.messages, streamName, topicName)
    }
    override suspend fun removeReactionInMessage(
        messageId: Int,
        emojiName: String
    ): UpdateReactionResponse  {
        val response = messageApiClient.removeReactionInMessage(messageId, emojiName)
        saveSentMessageInDB(messageId)
        return response
    }

    private fun saveMessagesInDb(messages: List<MessageWithReaction>) {
        local.saveMessagesWithReaction(messages)
    }

    private suspend fun saveSentMessageInDB(id: Int) {
        val messageResponse =  messageApiClient.getMessageById(id.toString())

        val sentMessage = messageResponse.messages.first()
        val streamName =  sentMessage.display_recipient
        val topicName = sentMessage.subject

        val messages = messageResponse.messages
        val messagesWithReactions = mapper.toMessageWithReactions(messages, streamName, topicName)

        local.saveMessagesWithReaction(messagesWithReactions)
    }
}
