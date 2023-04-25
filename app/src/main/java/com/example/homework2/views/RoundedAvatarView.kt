package com.example.homework2.views


import android.content.Context
import android.util.AttributeSet
import android.view.ViewOutlineProvider
import com.example.homework2.R


class RoundedAvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttrs) {

    init {
        scaleType = ScaleType.CENTER_CROP
        clipToOutline = true
        setBackgroundResource(R.drawable.bg_rounded_iv)
        outlineProvider = ViewOutlineProvider.BACKGROUND
    }
}
