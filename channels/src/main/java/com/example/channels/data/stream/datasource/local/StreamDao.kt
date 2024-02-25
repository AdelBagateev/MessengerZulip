package com.example.channels.data.stream.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.channels.data.stream.datasource.local.entity.StreamDB
import kotlinx.coroutines.flow.Flow

@Dao
interface StreamDao {
    @Query("SELECT * FROM stream WHERE is_subscribed LIKE :isSubscribed")
    fun getStreams(isSubscribed: Boolean): Flow<List<StreamDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStreams(list: List<StreamDB>)
}
