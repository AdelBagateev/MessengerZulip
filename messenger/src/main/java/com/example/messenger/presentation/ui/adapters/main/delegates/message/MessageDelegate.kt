package com.example.messenger.presentation.ui.adapters.main.delegates.message

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.common.adapter.interfaces.AdapterDelegate
import com.example.common.adapter.interfaces.DelegateItem
import com.example.messenger.R
import com.example.messenger.databinding.ItemMessageBinding
import com.example.messenger.domain.model.MessageModel
import com.example.messenger.presentation.ui.utils.addAuthHeader
import com.example.messenger.presentation.ui.utils.toEmojiViewList
import com.example.messenger.presentation.ui.views.EmojiView
import com.example.common.R as CommonR

class MessageDelegate(
    private val onMessageLongClick: (MessageModel) -> Unit,
    private val onReactionClick: (MessageModel, EmojiView) -> Unit,
    private val onPlusBtnClick: (MessageModel) -> Unit,
    private val onTopicClick: (String) -> Unit,
    private val isTopicSelected: Boolean
) : AdapterDelegate<ViewHolder, MessageDelegateItem> {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        getDelegateItem: (Int) -> DelegateItem
    ): ViewHolder =
        ViewHolder(
            onReactionClick,
            isTopicSelected,
            ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ).apply {
            setupActions(this, binding, getDelegateItem)
        }

    override fun onBindViewHolder(
        holder: ViewHolder,
        item: MessageDelegateItem,
    ) {
        holder.bind(item.value)
    }

    override fun isOfViewType(item: Any): Boolean {
        return item is MessageDelegateItem
    }

    private fun setupActions(
        viewHolder: ViewHolder,
        binding: ItemMessageBinding,
        getDelegateItem: (Int) -> DelegateItem
    ) {
        binding.apply {
            msgWithEmojis.messageLayout.setOnLongClickListener {
                val messageDelegate =
                    getDelegateItem(viewHolder.adapterPosition) as MessageDelegateItem
                onMessageLongClick(messageDelegate.value)
                true
            }
            msgWithEmojis.reactionsLayout.plusButton.setOnClickListener {
                val messageDelegate =
                    getDelegateItem(viewHolder.adapterPosition) as MessageDelegateItem
                onPlusBtnClick(messageDelegate.value)
            }
            msgWithEmojis.messageLayout.topicName.setOnClickListener {
                val messageDelegate =
                    getDelegateItem(viewHolder.adapterPosition) as MessageDelegateItem
                onTopicClick(messageDelegate.value.topicName)
            }
        }
    }
}

class ViewHolder(
    private val onReactionClick: (MessageModel, EmojiView) -> Unit,
    private val isTopicSelected: Boolean,
    val binding: ItemMessageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: MessageModel) {
        with(binding) {
            setupTopicName(model)
            setupReactions(model)
            setupMedia(model)
            if (model.isMy) {
                setupMyMessage(model)
            } else {
                setupOtherMessage(model)
            }
        }
    }

    private fun ItemMessageBinding.setupMedia(model: MessageModel) {
        if (model.mediaContent != null) {
            msgWithEmojis.messageLayout.media.isVisible = true
            msgWithEmojis.messageLayout.media.load(model.mediaContent) {
                error(CommonR.drawable.ic_no_avatar)
                placeholder(R.drawable.ic_photo)
                addAuthHeader()
            }
        } else {
            msgWithEmojis.messageLayout.media.isVisible = false
        }
    }

    private fun ItemMessageBinding.setupReactions(model: MessageModel) {
        msgWithEmojis.reactionsLayout.emojis =
            model.reactions.toEmojiViewList(msgWithEmojis.context) {
                onReactionClick(model, it)
            }
    }

    private fun setupMyMessage(model: MessageModel) {
        binding.apply {
            val res = msgWithEmojis.layoutParams as? FrameLayout.LayoutParams

            msgWithEmojis.apply {
                messageLayout.message.text = model.content
                avatarView.isVisible = false
                messageLayout.senderName.isVisible = false
                messageLayout.setBackgroundResource(R.drawable.bg_my_message)
            }
            //сеттим сообщение справа
            res?.gravity = Gravity.END
        }
    }

    private fun setupOtherMessage(model: MessageModel) {
        binding.apply {
            val layoutParams = msgWithEmojis.layoutParams as? FrameLayout.LayoutParams

            msgWithEmojis.messageLayout.message.text = model.content
            msgWithEmojis.messageLayout.senderName.text = model.nameSender
            msgWithEmojis.avatarView.load(model.avatarUrl) {
                placeholder(CommonR.drawable.ic_profile)
                error(CommonR.drawable.ic_no_avatar)
            }
            msgWithEmojis.avatarView.isVisible = true
            msgWithEmojis.messageLayout.senderName.isVisible = true

            msgWithEmojis.messageLayout.setBackgroundResource(R.drawable.bg_other_message)
            // сеттим собщение слева
            layoutParams?.gravity = Gravity.START
        }
    }

    private fun ItemMessageBinding.setupTopicName(model: MessageModel) {
        if (!isTopicSelected) {
            msgWithEmojis.messageLayout.topicName.isVisible = true
            msgWithEmojis.messageLayout.topicName.text =
                root.context.getString(R.string.topic, model.topicName)
        } else {
            msgWithEmojis.messageLayout.topicName.isVisible = false
        }
    }
}
