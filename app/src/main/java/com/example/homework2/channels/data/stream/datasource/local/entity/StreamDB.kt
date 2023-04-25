package com.example.homework2.channels.data.stream.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["id", "is_subscribed"],tableName = "stream")
class StreamDB(
    val id: Int,
    val name: String,
    @ColumnInfo("is_subscribed")
    val isSubscribed : Boolean
)
