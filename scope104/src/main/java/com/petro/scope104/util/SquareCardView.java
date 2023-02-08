package com.petro.scope104.util;

import android.content.Context;
import android.util.AttributeSet;

import androidx.cardview.widget.CardView;

public class SquareCardView extends CardView {

    public SquareCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minSize = Math.min(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(minSize, minSize);
    }

}