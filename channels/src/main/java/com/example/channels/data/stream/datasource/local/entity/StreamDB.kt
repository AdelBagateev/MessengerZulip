package com.example.channels.data.stream.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["id", "is_subscribed"], tableName = "stream")
class StreamDB(
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("is_subscribed")
    val isSubscribed: Boolean
)
