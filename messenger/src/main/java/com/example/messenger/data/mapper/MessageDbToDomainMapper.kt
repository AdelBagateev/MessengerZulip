package com.example.messenger.data.mapper

import com.example.messenger.data.datasource.local.entity.MessageWithReaction
import com.example.messenger.data.datasource.local.entity.ReactionDB
import com.example.messenger.di.qualifiers.UrlLoadMedia
import com.example.messenger.domain.model.MessageModel
import com.example.messenger.domain.model.ReactionModel
import javax.inject.Inject

interface MessageDbToDomainMapper {
    fun toMessageModel(list: List<MessageWithReaction>): List<MessageModel>
}

internal class MessageDbToDomainMapperImpl @Inject constructor(
    private val mediaRegex: Regex,
    @UrlLoadMedia
    private val urlLoadMedia: String
) : MessageDbToDomainMapper {
    override fun toMessageModel(list: List<MessageWithReaction>): List<MessageModel> {

        return list.map {
            val msg = it.message
            val reactions = it.reactions
            MessageModel(
                id = msg.id,
                timestamp = msg.timestamp,
                avatarUrl = msg.avatarUrl,
                nameSender = msg.nameSender,
                content = calculateContent(msg.content),
                mediaContent = calculateMediaContent(msg.content),
                reactions = reactions.toReactionModel(),
                isMy = msg.isMy,
                streamName = msg.streamName,
                topicName = msg.topicName
            )
        }
    }

    private fun calculateContent(content: String): String {
        val matches = mediaRegex.find(content) ?: return content
        val newContent = matches.groupValues[1]
        return newContent
    }

    private fun calculateMediaContent(content: String): String? {
        val matches = mediaRegex.find(content) ?: return null
        val mediaContent = matches.groupValues[2]
        return urlLoadMedia + mediaContent
    }

    private fun List<ReactionDB>.toReactionModel(): MutableList<ReactionModel> {
        return map {
            ReactionModel(it.emojiCode, it.userId, it.count, it.isSelected, it.emojiName)
        }.toMutableList()
    }
}
