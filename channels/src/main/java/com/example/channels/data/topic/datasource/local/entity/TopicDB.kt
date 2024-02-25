package com.example.channels.data.topic.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.example.channels.data.stream.datasource.local.entity.StreamDB

@Entity(primaryKeys = ["name", "stream_id"], tableName = "topic")
class TopicDB(
    @ColumnInfo("name")
    val name: String,
    @Embedded(prefix = "stream_")
    val stream: StreamDB
)
