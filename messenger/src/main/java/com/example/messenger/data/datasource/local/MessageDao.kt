package com.example.messenger.data.datasource.local

import androidx.room.*
import com.example.messenger.data.datasource.local.entity.MessageDB
import com.example.messenger.data.datasource.local.entity.MessageWithReaction
import com.example.messenger.data.datasource.local.entity.ReactionDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query(
        "SELECT * FROM message WHERE stream_name LIKE :streamName " +
                "AND topic_name LIKE :topicName"
    )
    fun getMessagesByStreamAndTopic(
        streamName: String,
        topicName: String
    ): Flow<List<MessageWithReaction>>

    @Query("SELECT * FROM message WHERE stream_name LIKE :streamName")
    fun getMessagesByStream(streamName: String): Flow<List<MessageWithReaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMessages(messages: List<MessageDB>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReactions(reactions: List<ReactionDB>)

    @Transaction
    suspend fun saveMessagesWithReaction(messagesWithReaction: List<MessageWithReaction>) {
        saveMessages(messagesWithReaction.map { it.message })
        messagesWithReaction.forEach {
            saveReactions(it.reactions)
        }
    }

    @Query(
        "DELETE FROM message WHERE stream_name LIKE :streamName " +
                "AND id NOT IN (SELECT id FROM message " +
                "WHERE stream_name LIKE :streamName " +
                "ORDER BY timestamp DESC LIMIT 50)"
    )
    suspend fun deleteOldMessages(streamName: String)

    @Query("DELETE FROM message WHERE id LIKE :messageId")
    suspend fun deleteMessageById(messageId: Int)
}

