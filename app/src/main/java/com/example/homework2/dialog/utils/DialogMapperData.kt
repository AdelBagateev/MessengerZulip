package com.example.homework2.dialog.utils

import com.example.homework2.BuildConfig
import com.example.homework2.dialog.data.datasource.local.entity.MessageDB
import com.example.homework2.dialog.data.datasource.local.entity.MessageWithReaction
import com.example.homework2.dialog.data.datasource.local.entity.ReactionDB
import com.example.homework2.dialog.data.datasource.remote.response.Message
import com.example.homework2.dialog.data.datasource.remote.response.Reaction
import com.example.homework2.dialog.domain.model.MessageModel
import com.example.homework2.dialog.domain.model.ReactionModel
import javax.inject.Inject

interface DialogMapperData {
   fun toMessageModel(list: List<MessageWithReaction>): List<MessageModel>
   fun toMessageWithReactions(messages: List<Message>, streamName: String, topicName: String): List<MessageWithReaction>
}
class DialogMapperDataImpl @Inject constructor() : DialogMapperData {
    private val regex = Regex(PATTERN_OF_REGEX)
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
                reactions = reactions.toReactionModel() ,
                isMy = msg.isMy,
            )
        }
    }

    private fun calculateContent(content: String) : String {
        val matches = regex.find(content) ?: return content
        val newContent = matches.groupValues[1]
        return newContent
    }
    private fun calculateMediaContent(content: String) : String? {
        val matches = regex.find(content) ?: return null
        val mediaContent = matches.groupValues[2]
        return BASE_URL + mediaContent
    }

    override fun toMessageWithReactions(
        messages: List<Message>,
        streamName: String,
        topicName: String
    ): List<MessageWithReaction> {
        return messages.map { it.toMessageWithReaction(streamName, topicName) }
    }
    private fun Message.toMessageWithReaction(streamName: String, topicName: String): MessageWithReaction{
        return MessageWithReaction(
            message = MessageDB(
                id = id,
                timestamp = timestamp,
                avatarUrl = avatar_url,
                nameSender = sender_full_name,
                content = content,
                isMy = sender_id == BuildConfig.ID,
                streamName = streamName,
                topicName = topicName,
            ),
            reactions = reactions.toReactionDb(id)
        )
    }
    private fun List<ReactionDB>.toReactionModel(): MutableList<ReactionModel> {
        return map {
            ReactionModel(it.emojiCode, it.userId, it.count, it.isSelected, it.emojiName)
        }.toMutableList()
    }
    private fun List<Reaction>.toReactionDb(id: Int): List<ReactionDB> {
        val newList = this.distinctBy { it.emoji_code }
        val listOfId = this.map { it.emoji_code to it.user_id }
        return newList.map {
            ReactionDB(
                messageId = id,
                emojiCode = ReactionsRepo.getCodeString(it.emoji_code),
                userId = it.user_id,
                count = this.count { reaction -> it.emoji_code == reaction.emoji_code },
                isSelected = listOfId.contains(it.emoji_code to BuildConfig.ID),
                emojiName = it.emoji_name
            )
        }.filter { it.emojiCode.isNotBlank()  }
    }
    companion object{
        private const val BASE_URL = "https://tinkoff-android-spring-2023.zulipchat.com"
        private const val PATTERN_OF_REGEX = "\\[(.*?)\\]\\((/user_uploads.*?\\.(jpe?g|png|gif))\\)"
    }
}
