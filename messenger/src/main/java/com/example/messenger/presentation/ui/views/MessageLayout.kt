package com.example.messenger.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginTop
import com.example.messenger.R

class MessageLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttrs, defStyleRes) {

    val topicName: TextView
    val senderName: TextView
    val message: TextView
    val media: ImageView

    private var topicNameWidth = 0
    private var topicNameHeight = 0
    private var senderNameWidth = 0
    private var senderNameHeight = 0
    private var messageWidth = 0
    private var messageHeight = 0
    private var mediaWidth = 0
    private var mediaHeight = 0

    private var messageMarginTop = 0

    init {
        inflate(context, R.layout.message_layout, this)
        topicName = findViewById(R.id.topic_name)
        senderName = findViewById(R.id.sender_name)
        message = findViewById(R.id.message)
        media = findViewById(R.id.iv_media)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildWithMargins(topicName, widthMeasureSpec, 0, heightMeasureSpec, 0)
        measureChildWithMargins(senderName, widthMeasureSpec, 0, heightMeasureSpec, 0)
        measureChildWithMargins(message, widthMeasureSpec, 0, heightMeasureSpec, 0)
        measureChildWithMargins(media, widthMeasureSpec, 0, heightMeasureSpec, 0)

        val horizontalPadding = paddingLeft + paddingStart
        val verticalPadding = paddingBottom + paddingTop


        topicNameWidth = if (topicName.visibility != GONE) topicName.measuredWidth else 0
        topicNameHeight = if (topicName.visibility != GONE) topicName.measuredHeight else 0

        senderNameWidth = if (senderName.visibility != GONE) senderName.measuredWidth else 0
        senderNameHeight = if (senderName.visibility != GONE) senderName.measuredHeight else 0

        messageMarginTop = if (senderName.visibility != GONE) message.marginTop else 0

        messageWidth = if (message.visibility != GONE) message.measuredWidth else 0
        messageHeight = if (message.visibility != GONE) message.measuredHeight else 0

        mediaWidth = if (media.visibility != GONE) media.measuredWidth else 0
        mediaHeight = if (media.visibility != GONE) media.measuredHeight else 0

        val width =
            maxOf(senderNameWidth, messageWidth, mediaWidth, topicNameWidth) + horizontalPadding
        val height =
            topicNameHeight + senderNameHeight + messageHeight + mediaHeight + messageMarginTop + verticalPadding
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        val topicNameTop = paddingTop
        val topicNameLeft = paddingLeft
        val topicNameRight = topicNameLeft + topicNameWidth
        val topicNameBottom = topicNameTop + topicNameHeight
        topicName.layout(topicNameLeft, topicNameTop, topicNameRight, topicNameBottom)

        val usernameTop = topicNameBottom
        val usernameLeft = paddingLeft
        val usernameRight = usernameLeft + senderNameWidth
        val usernameBottom = usernameTop + senderNameHeight
        senderName.layout(usernameLeft, usernameTop, usernameRight, usernameBottom)

        val messageTop = usernameBottom + messageMarginTop
        val messageLeft = paddingLeft
        val messageRight = messageLeft + messageWidth
        val messageBottom = messageTop + messageHeight + messageMarginTop
        message.layout(messageLeft, messageTop, messageRight, messageBottom)

        val mediaTop = messageBottom
        val mediaLeft = paddingLeft
        val mediaRight = mediaLeft + mediaWidth
        val mediaBottom = mediaTop + mediaHeight
        media.layout(mediaLeft, mediaTop, mediaRight, mediaBottom)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: LayoutParams): Boolean {
        return p is MarginLayoutParams
    }
}
