package com.example.messenger.presentation.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.example.messenger.R
import com.example.messenger.presentation.ui.views.utils.dp
import com.example.messenger.presentation.ui.views.utils.sp
import kotlin.properties.Delegates

class EmojiView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attributeSet, defStyleAttr, defStyleRes), View.OnClickListener {

    var isPlusButton = false

    private lateinit var textPaint: Paint
    private lateinit var textToDraw: String
    var setDataInMessageModel: (EmojiView) -> (Unit) = {}

    var counterValue by Delegates.notNull<Int>()
    var emoji: String = DEFAULT_EMOJI

    private var textBounds = Rect()

    init {
        setPaddings()
        initDefaultAttributes()
        initPaint()
        setPaddings()
        setOnClickListener(this)
    }

    private fun setPaddings() {
        setPadding(
            PADDING_LEFT.dp(context), PADDING_TOP.dp(context),
            PADDING_RIGHT.dp(context), PADDING_BOTTOM.dp(context)
        )
    }

    private fun initPaint() {
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = context.getColor(R.color.count_selected)
            style = Paint.Style.FILL
            textSize = DEFAULT_TEXT_SIZE.sp(context)
        }
    }

    private fun initDefaultAttributes() {
        counterValue = DEFAULT_COUNTER_VALUE
        emoji = DEFAULT_EMOJI
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textToDraw = "$emoji $counterValue"

        textPaint.getTextBounds(
            textToDraw,
            0,
            textToDraw.length,
            textBounds
        )

        val textWidth = textBounds.width()
        val textHeight = textBounds.height()

        val totalWidth = textWidth + paddingLeft + paddingRight
        val totalHeight = textHeight + paddingTop + paddingBottom

        val measuredWidth = resolveSize(totalWidth, widthMeasureSpec)
        val measuredHeight = resolveSize(totalHeight, heightMeasureSpec)

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        val centerY = height / 2 - textBounds.exactCenterY()
        //кнопка плюс
        if (isPlusButton) {
            textToDraw = "   +"
            setBackgroundResource(R.drawable.bg_reaction_view_default)
            //эмодзи нажато
        } else if (isSelected) {
            setBackgroundResource(R.drawable.bg_reaction_view_selected)
            //эмодзи не нажато
        } else {
            setBackgroundResource(R.drawable.bg_reaction_view_default)
        }
        canvas.drawText(textToDraw, paddingLeft.toFloat(), centerY, textPaint)
    }

    override fun onClick(v: View) {
        val emojiView = v as EmojiView
        if (!emojiView.isPlusButton) {
            updateCounter()
        }
    }

    private fun updateCounter() {
        isSelected = !isSelected
        if (isSelected) {
            counterValue++
        } else {
            counterValue--
        }
        if (counterValue < 1) {
            visibility = GONE
        }
        setDataInMessageModel(this)
        requestLayout()
    }

    companion object {
        private const val DEFAULT_EMOJI = "\uD83D\uDE80"
        private const val DEFAULT_COUNTER_VALUE = 1
        private const val DEFAULT_TEXT_SIZE = 16f
        private const val PADDING_LEFT = 12f
        private const val PADDING_RIGHT = 15f
        private const val PADDING_TOP = 9f
        private const val PADDING_BOTTOM = 9f
    }
}
