package com.example.messenger.presentation.ui.views.utils

import android.content.Context
import android.util.TypedValue
import com.example.messenger.presentation.ui.views.EmojiView
import com.example.messenger.presentation.ui.views.FlexBoxLayout

fun Float.sp(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    this,
    context.resources.displayMetrics
)

fun Float.dp(context: Context) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    context.resources.displayMetrics
).toInt()

fun FlexBoxLayout.addViews(reactions: List<EmojiView>) {
    reactions.forEach { addView(it) }
}
