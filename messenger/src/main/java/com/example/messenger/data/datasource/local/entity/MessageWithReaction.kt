package com.example.messenger.data.datasource.local.entity

import androidx.room.Embedded
import androidx.room.Relation

class MessageWithReaction(
    @Embedded
    val message: MessageDB,
    @Relation(
        parentColumn = "id",
        entityColumn = "message_id",
        entity = ReactionDB::class
    )
    val reactions: List<ReactionDB>
)
