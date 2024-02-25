package com.example.messenger.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import com.example.messenger.presentation.ui.views.utils.addViews
import com.example.messenger.presentation.ui.views.utils.dp

class FlexBoxLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attributeSet, defStyleAttr, defStyleRes) {

    val plusButton = EmojiView(context).apply {
        isPlusButton = true
    }


    private val horizontalSpacing = HORIZONTAL_SPACING.dp(context)
    private val verticalSpacing = VERTICAL_SPACING.dp(context)

    var emojis = emptyList<EmojiView>()
        set(value) {
            field = value
            removeAllViews()
            if (field.isNotEmpty()) {
                addViews(value)
                addView(plusButton)
                plusButton.visibility = VISIBLE
            }
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var counter = 0

        for (child in children) {
            if (child.visibility != GONE) {
                counter++
            }
        }

        if (counter == 1) {
            plusButton.visibility = GONE
        }

        val width = MeasureSpec.getSize(widthMeasureSpec)
        var height = paddingTop + paddingBottom
        var rowWidth = paddingLeft
        var rowHeight = 0

        measureChildren(widthMeasureSpec, heightMeasureSpec)
        var maxRowWith = 0
        for (child in children) {
            if (rowWidth > maxRowWith) {
                maxRowWith = rowWidth
            }
            if (child.visibility != GONE) {
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight
                //переносим строку
                if (rowWidth + childWidth > width) {
                    rowWidth = paddingLeft + childWidth
                    height += rowHeight + verticalSpacing
                    rowHeight = childHeight
                    //не переносим строку
                } else {
                    rowWidth += childWidth + horizontalSpacing
                    maxRowWith = maxOf(rowWidth, maxRowWith)
                    rowHeight = maxOf(rowHeight, childHeight)
                }
                //отдельный случай с кнопкой "+"
                if (children.indexOf(child) == childCount - 1) {
                    if (rowWidth + childWidth > width) {
                        height += rowHeight
                        rowHeight = 0
                    }
                    rowWidth += childWidth + horizontalSpacing
                    height += rowHeight
                }
            }
        }
        setMeasuredDimension(maxRowWith, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var rowWidth = paddingLeft
        var rowHeight = 0
        var top = paddingTop
        var left = paddingLeft

        for (child in children) {
            if (child.visibility != GONE) {
                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight
                //переносим строки
                if (rowWidth + childWidth > width) {
                    top += rowHeight
                    rowHeight = childHeight + verticalSpacing
                    rowWidth = paddingLeft + childWidth + horizontalSpacing
                    left = paddingLeft
                    // не переносим строку
                } else {
                    rowWidth += childWidth + horizontalSpacing
                    rowHeight = maxOf(rowHeight, childHeight + verticalSpacing)
                }

                //последняя кнопка "+"
                if (children.indexOf(child) == (childCount - 1)) {
                    removeView(plusButton)
                    addView(plusButton, childCount)
                    child.layout(left, top, left + childWidth, top + childHeight)
                    //все эмодзи перед кнопкой "+"
                } else {
                    child.layout(left, top, left + childWidth, top + childHeight)
                    left += childWidth + horizontalSpacing
                }
            }
        }
    }


    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }


    companion object {
        private const val HORIZONTAL_SPACING = 10f
        private const val VERTICAL_SPACING = 7f
    }
}


