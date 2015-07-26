package com.cettco.coreutils.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cettco on 7/26/15.
 */

/**
 * TODO(cettco)
 * Add Gravity
 */
public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        int layoutHeight = MeasureSpec.getSize(heightMeasureSpec);
        int layoutWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int layoutHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int currentLineWidth = 0;
        int currentLineHeight = 0;
        int totalHeight = 0;
        int totalWidth = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin
                    + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin
                    + lp.bottomMargin;
            if ((currentLineWidth + childWidth) > layoutWidth) {
                totalHeight += currentLineHeight;
                totalWidth = Math.max(totalWidth, currentLineWidth);
                currentLineWidth = 0;
                lp.x = currentLineWidth + lp.leftMargin;
                lp.y = totalHeight + lp.topMargin;
                currentLineWidth = childWidth;
                currentLineHeight = 0;
                currentLineHeight = Math.max(currentLineHeight, childHeight);
            } else {
                lp.x = currentLineWidth + lp.leftMargin;
                lp.y = totalHeight + lp.topMargin;
                currentLineWidth += childWidth;

                currentLineHeight = Math.max(currentLineHeight, childHeight);
            }
            if (i == count - 1) {
                totalWidth = Math.max(totalWidth, currentLineWidth);
                totalHeight += currentLineHeight;
            }
        }
        setMeasuredDimension(
                layoutWidthMode == MeasureSpec.EXACTLY ? layoutWidth
                        : totalWidth,
                layoutHeightMode == MeasureSpec.EXACTLY ? layoutHeight
                        : totalHeight);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int count = getChildCount();
        for(int index = 0;index<count;index++){
            View child = getChildAt(index);
            LayoutParams lp = (LayoutParams)child.getLayoutParams();
            child.layout(lp.x,lp.y,lp.x+child.getMeasuredWidth(),lp.y+child.getMeasuredHeight());
        }

    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(),attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public int x;
        public int y;

        public LayoutParams(int width, int height) {
            super(width, height);
        }
        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }
    }
}
