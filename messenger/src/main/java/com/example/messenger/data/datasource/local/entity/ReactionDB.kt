package com.example.messenger.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["message_id", "emoji_name"],
    tableName = "reaction",
    foreignKeys = [
        ForeignKey(
            entity = MessageDB::class, parentColumns = ["id"],
            childColumns = ["message_id"], onDelete = ForeignKey.CASCADE
        )
    ]
)
class ReactionDB(
    @ColumnInfo("message_id")
    val messageId: Int,
    @ColumnInfo("emoji_code")
    val emojiCode: String,
    @ColumnInfo("user_id")
    val userId: Int,
    @ColumnInfo("count")
    val count: Int,
    @ColumnInfo("isSelected")
    val isSelected: Boolean = false,
    @ColumnInfo("emoji_name")
    val emojiName: String
)
