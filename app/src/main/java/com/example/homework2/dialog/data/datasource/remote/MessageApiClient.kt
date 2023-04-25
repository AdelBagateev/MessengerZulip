package com.example.homework2.dialog.data.datasource.remote

import com.example.homework2.dialog.data.datasource.remote.request.Narrow
import com.example.homework2.dialog.data.datasource.remote.response.MessagesResponse
import com.example.homework2.dialog.data.datasource.remote.response.SendMediaMessageResponse
import com.example.homework2.dialog.data.datasource.remote.response.SendMessageResponse
import com.example.homework2.dialog.data.datasource.remote.response.UpdateReactionResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class MessageApiClient @Inject constructor(
    private val api: DialogApi
) {

    suspend fun getMessagesByStreamAndTopic(
        streamName: String,
        topicName: String,
        anchor: String,
    ): MessagesResponse {
        val query = createQueryGetMessages(streamName, topicName, anchor)
        return api.getMessagesByStream(query)
    }
    suspend fun getMessageById(
        id: String,
    ): MessagesResponse {
        val query = createQueryForOneMsg(id)
        return api.getMessagesByStream(query)
    }
    suspend fun sendMessage(
        content: String,
        streamName: String,
        topicName: String
    ): SendMessageResponse {
        val query = createQuerySendMessage(streamName, content, topicName)
        return api.sendMessage(query)
    }

    suspend fun addReactionInMessage(messageId: Int, emojiName: String): UpdateReactionResponse {
        return api.addReactionInMessage(messageId, emojiName)
    }

    suspend fun removeReactionInMessage(messageId: Int, emojiName: String): UpdateReactionResponse {
        return api.removeReactionInMessage(messageId, emojiName)
    }

    suspend fun uploadFile(file: File): SendMediaMessageResponse {
        val mediaType =  "image/*".toMediaTypeOrNull()
        val requestFile = RequestBody.create(mediaType, file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        return api.uploadFile(body)
    }

    private fun createQuerySendMessage(
        streamName: String,
        content: String,
        topicName: String
    ): Map<String, String> {
        return mapOf(
            "type" to "stream",
            "to" to streamName,
            "content" to content,
            "topic" to topicName
        )
    }

    private fun createQueryGetMessages(
        streamName: String,
        topicName: String,
        anchor: String,
        numBefore: String = MESSAGE_COUNT
    ): Map<String, String> {
        val narrow = listOf(
            Narrow("stream", streamName),
            Narrow("topic", topicName),
        )

        return mapOf(
            "narrow" to Json.encodeToString(narrow),
            "num_before" to numBefore,
            "num_after" to "0",
            "anchor" to anchor,
            "apply_markdown" to "false"
        )
    }


    private fun createQueryForOneMsg(
        id: String,
    ): Map<String, String> {
        return mapOf(
            "num_before" to "0",
            "num_after" to "0",
            "anchor" to id,
            "apply_markdown" to "false"
        )
    }

    companion object {
        private const val MESSAGE_COUNT = "20"
    }
}
