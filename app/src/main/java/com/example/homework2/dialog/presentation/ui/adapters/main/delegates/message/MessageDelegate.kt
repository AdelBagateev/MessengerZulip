package com.example.homework2.dialog.presentation.ui.adapters.main.delegates.message

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework2.BuildConfig
import com.example.homework2.R
import com.example.homework2.common.adapter.interfaces.AdapterDelegate
import com.example.homework2.databinding.ItemMessageBinding
import com.example.homework2.dialog.domain.model.MessageModel
import com.example.homework2.dialog.utils.toEmojiViewList
import com.example.homework2.views.EmojiView

class MessageDelegate(
    private val showBottomSheet: (MessageModel) -> Unit,
    private val changeEmojiCounterValue: (MessageModel, EmojiView) -> Unit
) : AdapterDelegate<ViewHolder, MessageDelegateItem> {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder =
        ViewHolder(
            showBottomSheet,
            changeEmojiCounterValue,
            ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        item: MessageDelegateItem,
    ) {
        holder.bind(item.value)
    }

    override fun isOfViewType(item: Any): Boolean {
        return item is MessageDelegateItem
    }

}

class ViewHolder(
    private val showBottomSheet: (MessageModel) -> Unit,
    private val changeEmojiCounterValue: (MessageModel, EmojiView) -> Unit,
    private val binding: ItemMessageBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: MessageModel) {
        with(binding) {
            setupReactions(model)
            setupActions(model)
            setupMedia(model)
            if (model.isMy) {
                setupMyMessage(model)
            } else {
                setupOtherMessage(model)
            }
        }
    }
    private fun ItemMessageBinding.setupMedia(model: MessageModel) {
        if(model.mediaContent != null) {
            msgWithEmojis.messageLayout.media.isVisible = true
            msgWithEmojis.messageLayout.media.load(model.mediaContent){
                error(R.drawable.ic_no_avatar)
                placeholder(R.drawable.ic_photo)
                addHeader("Authorization", BuildConfig.API_KEY)
            }
        } else {
            msgWithEmojis.messageLayout.media.isVisible = false
        }
    }
    private fun ItemMessageBinding.setupActions(model: MessageModel) {
        msgWithEmojis.messageLayout.setOnLongClickListener {
            showBottomSheet(model)
            true
        }
        msgWithEmojis.reactionsLayout.plusButton.setOnClickListener {
            showBottomSheet(model)
        }
    }

    private fun ItemMessageBinding.setupReactions(model: MessageModel) {
        msgWithEmojis.reactionsLayout.emojis =
            model.reactions.toEmojiViewList(msgWithEmojis.context) {
                changeEmojiCounterValue(
                    model,
                    it
                )
            }
    }

    private fun setupMyMessage(model: MessageModel) {
        binding.apply {
            val res = msgWithEmojis.layoutParams as? FrameLayout.LayoutParams

            msgWithEmojis.apply {
                messageLayout.message.text = model.content
                avatarView.isVisible = false
                messageLayout.name.isVisible = false
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
            msgWithEmojis.messageLayout.name.text = model.nameSender
            msgWithEmojis.avatarView.load(model.avatarUrl) {
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_no_avatar)
            }
            msgWithEmojis.avatarView.isVisible = true
            msgWithEmojis.messageLayout.name.isVisible = true

            msgWithEmojis.messageLayout.setBackgroundResource(R.drawable.bg_other_message)
            // сеттим собщение слева
            layoutParams?.gravity = Gravity.START
        }
    }
}
