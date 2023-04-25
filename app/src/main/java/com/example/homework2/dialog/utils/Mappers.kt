package com.example.homework2.dialog.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import com.example.homework2.dialog.domain.model.ReactionModel
import com.example.homework2.views.EmojiView
import java.util.*

fun Int.toFormatDate(): String {
    val date = Date(this * 1000L)
    val formatter = SimpleDateFormat("dd MMMM", Locale.getDefault())
    return formatter.format(date)
}

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

fun List<EmojiNCS>.toReactionList(): List<ReactionModel> =
    map {
        ReactionModel(ReactionsRepo.getCodeString(it.code), 1, 1, true, it.name)
    }
