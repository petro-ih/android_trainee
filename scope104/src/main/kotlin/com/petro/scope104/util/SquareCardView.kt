package com.petro.scope104.util

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class SquareCardView(context: Context?, attrs: AttributeSet?) : CardView(
    context!!, attrs
) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minSize = Math.min(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(minSize, minSize)
    }
}