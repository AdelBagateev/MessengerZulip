package com.example.homework2.dialog.data.datasource.local

import androidx.room.*
import com.example.homework2.dialog.data.datasource.local.entity.MessageDB
import com.example.homework2.dialog.data.datasource.local.entity.MessageWithReaction
import com.example.homework2.dialog.data.datasource.local.entity.ReactionDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query(
        "SELECT * FROM message WHERE stream_name LIKE :streamName " +
                "AND topic_name LIKE :topicName"
    )
    fun getMessages(streamName: String, topicName: String): Flow<List<MessageWithReaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMessages(messages: List<MessageDB>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveReactions(reactions: List<ReactionDB>)

    @Transaction
    fun saveMessagesWithReaction(messagesWithReaction: List<MessageWithReaction>) {
        saveMessages(messagesWithReaction.map { it.message })
        messagesWithReaction.forEach {
            saveReactions(it.reactions)
        }
    }
    @Query("DELETE FROM message WHERE stream_name LIKE :streamName AND topic_name LIKE :topicName " +
            "AND id NOT IN (SELECT id FROM message " +
            "WHERE stream_name LIKE :streamName AND topic_name LIKE :topicName " +
            "ORDER BY timestamp DESC LIMIT 50)"
    )
    fun deleteOldMessages(streamName: String, topicName: String)
}

