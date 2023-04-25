package com.example.homework2.dialog.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
class MessageDB(
    @PrimaryKey
    val id: Int,
    @ColumnInfo("stream_name")
    val streamName: String,
    @ColumnInfo("topic_name")
    val topicName: String,
    val timestamp: Int,
    @ColumnInfo("avatar_url")
    val avatarUrl: String?,
    @ColumnInfo("name_sender")
    val nameSender: String?,
    val content: String,
    @ColumnInfo("is_my")
    val isMy: Boolean
)
