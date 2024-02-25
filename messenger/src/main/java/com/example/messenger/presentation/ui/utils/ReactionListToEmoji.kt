package com.example.messenger.presentation.ui.utils

import android.content.Context
import com.example.messenger.domain.model.ReactionModel
import com.example.messenger.presentation.ui.views.EmojiView

fun List<ReactionModel>.toEmojiViewList(
    context: Context,
    updateDataInMessageModel: (EmojiView) -> Unit
): List<EmojiView> =
    map {
        EmojiView(context).apply {
            emoji = it.emojiCode
            counterValue = it.count
            isSelected = it.isSelected
            setDataInMessageModel = { updateDataInMessageModel(it) }
        }
    }
