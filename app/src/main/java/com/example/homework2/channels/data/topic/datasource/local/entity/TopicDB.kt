package com.example.homework2.channels.data.topic.datasource.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import com.example.homework2.channels.data.stream.datasource.local.entity.StreamDB

@Entity(primaryKeys = ["name", "stream_id"], tableName = "topic")
class TopicDB(
    val name: String,
    @Embedded(prefix = "stream_")
    val stream: StreamDB
)
