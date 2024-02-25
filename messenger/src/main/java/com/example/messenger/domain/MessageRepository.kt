package com.example.messenger.domain

import com.example.messenger.data.datasource.remote.response.SendMessageResponse
import com.example.messenger.data.datasource.remote.response.UpdateReactionResponse
import com.example.messenger.domain.model.MessageModel
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MessageRepository {
    suspend fun getMessagesByStreamAndTopic(streamName: String, topicName: String?, anchor: String)
    fun subscribeOnMessagesByStreamAndTopic(streamName: String, topicName: String?):
            Flow<List<MessageModel>>

    suspend fun sendMessage(
        content: String,
        streamName: String,
        topicName: String
    ): SendMessageResponse

    suspend fun sendMediaMessage(
        file: File,
        streamName: String,
        topicName: String
    )

    suspend fun updateMessage(messageId: Int, content: String, topicName: String)
    suspend fun deleteMessageById(messageId: Int)
    suspend fun deleteOldMessagesInDB(streamName: String)
    suspend fun addReactionToMessage(messageId: Int, emojiName: String): UpdateReactionResponse
    suspend fun removeReactionInMessage(messageId: Int, emojiName: String): UpdateReactionResponse
}
