package com.example.messenger.data.mapper

import com.example.common.BuildConfig
import com.example.messenger.data.datasource.local.entity.MessageDB
import com.example.messenger.data.datasource.local.entity.MessageWithReaction
import com.example.messenger.data.datasource.local.entity.ReactionDB
import com.example.messenger.data.datasource.remote.response.Message
import com.example.messenger.data.datasource.remote.response.Reaction
import com.example.messenger.presentation.ui.utils.ReactionsFactory
import javax.inject.Inject

interface MessageApiToDbMapper {
    fun toMessageWithReactions(
        messages: List<Message>,
    ): List<MessageWithReaction>
}

internal class MessageApiToDbMapperImpl @Inject constructor() : MessageApiToDbMapper {
    override fun toMessageWithReactions(
        messages: List<Message>,
    ): List<MessageWithReaction> {
        return messages.map { it.toMessageWithReaction() }
    }

    private fun Message.toMessageWithReaction(): MessageWithReaction {
        return MessageWithReaction(
            message = MessageDB(
                id = id,
                timestamp = timestamp,
                avatarUrl = avatarUrl,
                nameSender = senderFullName,
                content = content,
                isMy = senderId == BuildConfig.ID,
                streamName = displayRecipient,
                topicName = subject,
            ),
            reactions = reactions.toReactionDb(id)
        )
    }

    private fun List<Reaction>.toReactionDb(id: Int): List<ReactionDB> {
        val newList = this.distinctBy { it.emoji_code }
        val listOfId = this.map { it.emoji_code to it.user_id }
        return newList.map {
            ReactionDB(
                messageId = id,
                emojiCode = ReactionsFactory.getCodeString(it.emoji_code),
                userId = it.user_id,
                count = this.count { reaction -> it.emoji_code == reaction.emoji_code },
                isSelected = listOfId.contains(it.emoji_code to BuildConfig.ID),
                emojiName = it.emoji_name
            )
        }.filter { it.emojiCode.isNotBlank() }
    }
}
