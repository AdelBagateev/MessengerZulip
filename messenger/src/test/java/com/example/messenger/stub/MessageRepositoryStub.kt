package com.example.messenger.stub

import com.example.messenger.data.datasource.remote.response.SendMessageResponse
import com.example.messenger.data.datasource.remote.response.UpdateReactionResponse
import com.example.messenger.domain.MessageRepository
import com.example.messenger.domain.model.MessageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import java.io.File

class MessageRepositoryStub : MessageRepository {
    private val messagesFromDB = MutableStateFlow<List<MessageModel>>(emptyList())

    var loadMessagesResultProvider: (() -> List<MessageModel>) = {
        emptyList()
    }
    var reactionResultProvider: (() -> List<MessageModel>) = {
        emptyList()
    }
    var throwException = false

    override suspend fun getMessagesByStreamAndTopic(
        streamName: String,
        topicName: String?,
        anchor: String
    ) {
        if (throwException) {
            throw Exception()
        }
        messagesFromDB.emit(loadMessagesResultProvider())
    }

    override fun subscribeOnMessagesByStreamAndTopic(
        streamName: String,
        topicName: String?
    ): Flow<List<MessageModel>> {
        return messagesFromDB
            .filter { it.isNotEmpty() }
    }

    override suspend fun addReactionToMessage(
        messageId: Int,
        emojiName: String
    ): UpdateReactionResponse {
        messagesFromDB.emit(reactionResultProvider())
        return UpdateReactionResponse("")
    }

    override suspend fun removeReactionInMessage(
        messageId: Int,
        emojiName: String
    ): UpdateReactionResponse {
        messagesFromDB.emit(reactionResultProvider())
        return UpdateReactionResponse("")
    }

    override suspend fun sendMessage(
        content: String,
        streamName: String,
        topicName: String
    ): SendMessageResponse {
        TODO("Not yet implemented")
    }

    override suspend fun sendMediaMessage(file: File, streamName: String, topicName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMessage(messageId: Int, content: String, topicName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessageById(messageId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteOldMessagesInDB(streamName: String) {
        TODO("Not yet implemented")
    }

}
