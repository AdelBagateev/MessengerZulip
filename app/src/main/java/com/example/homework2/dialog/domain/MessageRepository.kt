package com.example.homework2.dialog.domain

import com.example.homework2.dialog.data.datasource.remote.response.SendMessageResponse
import com.example.homework2.dialog.data.datasource.remote.response.UpdateReactionResponse
import com.example.homework2.dialog.domain.model.MessageModel
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MessageRepository {
    suspend fun getMessagesByStreamAndTopic(streamName: String, topicName: String, anchor : String)
    fun subscribeOnMessagesByStreamAndTopic(streamName: String, topicName: String):
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
    suspend fun deleteOldMessages(streamName: String, topicName: String)
    suspend fun addReactionToMessage(messageId: Int, emojiName: String): UpdateReactionResponse
    suspend fun removeReactionInMessage(messageId: Int, emojiName: String): UpdateReactionResponse
}
