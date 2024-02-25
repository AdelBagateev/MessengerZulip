package com.example.messenger.data.datasource.remote

import com.example.messenger.data.datasource.remote.response.MessagesResponse
import com.example.messenger.data.datasource.remote.response.SendMediaMessageResponse
import com.example.messenger.data.datasource.remote.response.SendMessageResponse
import com.example.messenger.data.datasource.remote.response.UpdateReactionResponse
import okhttp3.MultipartBody
import retrofit2.http.*

internal interface MessengerApi {

    @GET("messages")
    suspend fun getMessagesByStream(@QueryMap map: Map<String, String>): MessagesResponse

    @POST("messages")
    suspend fun sendMessage(@QueryMap map: Map<String, String>): SendMessageResponse

    @POST("messages/{message_id}/reactions")
    suspend fun addReactionInMessage(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String
    ): UpdateReactionResponse

    @Multipart
    @POST("user_uploads")
    suspend fun uploadFile(@Part file: MultipartBody.Part): SendMediaMessageResponse

    @DELETE("messages/{message_id}/reactions")
    suspend fun removeReactionInMessage(
        @Path("message_id") messageId: Int,
        @Query("emoji_name") emojiName: String
    ): UpdateReactionResponse

    @PATCH("messages/{message_id}")
    suspend fun updateMessage(
        @Path("message_id") messageId: Int,
        @Query("content") content: String,
        @Query("topic") topicName: String
    )

    @DELETE("messages/{message_id}")
    suspend fun deleteMessage(
        @Path("message_id") messageId: Int,
    )
}
