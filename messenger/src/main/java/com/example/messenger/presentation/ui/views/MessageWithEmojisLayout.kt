package com.example.messenger.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import com.example.common.view.RoundedAvatarView
import com.example.messenger.R
import kotlin.properties.Delegates

class MessageWithEmojisLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes) {

    val avatarView: RoundedAvatarView
    val messageLayout: MessageLayout
    val reactionsLayout: FlexBoxLayout

    private var avatarWidth = 0
    private var avatarHeight = 0
    private var messageWidth = 0
    private var messageHeight = 0
    private var reactionsWidth = 0
    private var reactionsHeight = 0

    private lateinit var authorName: String
    private lateinit var messageText: String
    private var avatarImage by Delegates.notNull<Int>()

    init {
        inflate(context, R.layout.message_with_emojis, this)
        messageLayout = findViewById(R.id.messageLayout)
        avatarView = findViewById(R.id.iv_avatar)
        reactionsLayout = findViewById(R.id.reactions)
        if (attrs != null) {
            initAttributes(attrs, defStyleAttr, defStyleRes)
        } else {
            initDefaultAttributes()
        }
        setAttributes()
    }

    private fun setAttributes() {
        messageLayout.senderName.text = authorName
        messageLayout.message.text = messageText
        avatarView.setImageResource(avatarImage)
    }

    private fun initDefaultAttributes() {
        authorName = DEFAULT_NAME
        messageText = DEFAULT_MESSAGE
        avatarImage = DEFAULT_AVATAR
    }

    private fun initAttributes(attributeSet: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.MessageWithEmojisLayout,
            defStyleAttr,
            defStyleRes
        )

        authorName = typedArray.getString(R.styleable.MessageWithEmojisLayout_name) ?: DEFAULT_NAME
        messageText =
            typedArray.getString(R.styleable.MessageWithEmojisLayout_message) ?: DEFAULT_MESSAGE
        avatarImage =
            typedArray.getResourceId(R.styleable.MessageWithEmojisLayout_image, DEFAULT_AVATAR)
        typedArray.recycle()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildWithMargins(
            avatarView,
            widthMeasureSpec,
            0,
            heightMeasureSpec,
            0
        )
        measureChildWithMargins(
            messageLayout,
            widthMeasureSpec,
            avatarView.measuredWidth,
            heightMeasureSpec,
            0
        )
        measureChildWithMargins(
            reactionsLayout,
            widthMeasureSpec,
            avatarView.measuredWidth + reactionsLayout.marginLeft + avatarView.marginLeft,
            heightMeasureSpec,
            avatarView.measuredHeight + messageLayout.measuredHeight
        )

        avatarWidth = if (avatarView.visibility != GONE) avatarView.measuredWidth else 0
        avatarHeight = if (avatarView.visibility != GONE) avatarView.measuredHeight else 0
        messageWidth = if (messageLayout.visibility != GONE) messageLayout.measuredWidth else 0
        messageHeight = if (messageLayout.visibility != GONE) messageLayout.measuredHeight else 0
        reactionsWidth =
            if (reactionsLayout.visibility != GONE) reactionsLayout.measuredWidth else 0
        reactionsHeight =
            if (reactionsLayout.visibility != GONE) reactionsLayout.measuredHeight else 0

        val maxWidth = maxOf(avatarWidth + messageWidth, avatarWidth + reactionsWidth)
        val widthMargins =
            avatarView.marginLeft + maxOf(messageLayout.marginLeft, reactionsLayout.marginLeft)
        val desiredWidth = maxWidth + widthMargins

        val maxHeight = maxOf(avatarHeight, messageHeight + reactionsHeight)
        val heightMargin = messageLayout.marginTop + reactionsLayout.marginTop
        val desiredHeight = maxHeight + heightMargin

        val width = resolveSize(desiredWidth, widthMeasureSpec)
        val height = resolveSize(desiredHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        // Layout the child views
        avatarView.layout(
            0 + avatarView.marginLeft,
            0 + avatarView.marginTop,
            avatarWidth + avatarView.marginLeft,
            avatarHeight + avatarView.marginTop
        )
        messageLayout.layout(
            avatarWidth + messageLayout.marginLeft + avatarView.marginLeft,
            0 + messageLayout.marginTop,
            avatarWidth + messageWidth + messageLayout.marginLeft,
            messageHeight + messageLayout.marginTop
        )

        reactionsLayout.layout(
            avatarWidth + avatarView.marginLeft + reactionsLayout.marginLeft,
            messageHeight + messageLayout.marginTop + reactionsLayout.marginTop,
            avatarWidth + reactionsWidth + avatarView.marginLeft + reactionsLayout.marginLeft,
            messageHeight + reactionsHeight + messageLayout.marginTop + reactionsLayout.marginTop
        )
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

    companion object {
        private const val DEFAULT_NAME = "[NO NAME]"
        private const val DEFAULT_MESSAGE = "[NO MESSAGE]"
        private const val DEFAULT_AVATAR = 0
    }
}
