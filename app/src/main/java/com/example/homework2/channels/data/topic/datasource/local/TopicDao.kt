package com.example.homework2.channels.data.topic.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework2.channels.data.topic.datasource.local.entity.TopicDB
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Query("SELECT * FROM topic WHERE stream_id LIKE :streamId")
    fun getTopics(streamId: Int): Flow<List<TopicDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTopics(list: List<TopicDB>)
}
