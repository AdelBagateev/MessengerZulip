package com.example.common.adapter.decorators

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.annotation.Dimension
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecorator(
    context: Context,
    @Dimension(unit = Dimension.DP)
    spacingDp: Float,
    private val firstElemSpacing: Boolean = false,
    @Dimension(unit = Dimension.DP)
    verticalSpacingDp: Float = 0f,
) : RecyclerView.ItemDecoration() {

    private val spacingPx: Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        spacingDp,
        context.resources.displayMetrics
    ).toInt()

    private val verticalSpacingPx: Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        verticalSpacingDp,
        context.resources.displayMetrics
    ).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spacingMiddle = (spacingPx * 0.5).toInt()

        val viewHolder = parent.getChildViewHolder(view)
        val currentPosition = parent.getChildAdapterPosition(view).takeIf {
            it != RecyclerView.NO_POSITION
        } ?: viewHolder.oldPosition

        when (currentPosition) {
            0 -> {
                outRect.top = if (firstElemSpacing) spacingMiddle else 0
                outRect.bottom = spacingMiddle
            }
            state.itemCount - 1 -> {
                outRect.top = spacingMiddle
                outRect.bottom = spacingPx
            }
            else -> {
                outRect.top = spacingMiddle
                outRect.bottom = spacingMiddle
            }
        }
        outRect.left = verticalSpacingPx
        outRect.right = verticalSpacingPx
    }
}
