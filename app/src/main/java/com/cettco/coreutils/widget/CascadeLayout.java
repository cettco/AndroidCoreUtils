package com.cettco.coreutils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.cettco.coreutils.R;

/**
 * Created by cettco on 7/25/15.
 */
public class CascadeLayout extends ViewGroup {
    private int horizontal_spacing;
    private int vertical_spacing;
    public CascadeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CascadeLayout);
        horizontal_spacing = typedArray.getDimensionPixelSize(R.styleable.CascadeLayout_horizontal_spacing, getResources().getDimensionPixelSize(R.dimen.default_horizontal_spacing));
        vertical_spacing = typedArray.getDimensionPixelSize(R.styleable.CascadeLayout_vertical_spacing,getResources().getDimensionPixelSize(R.dimen.default_vertical_spacing));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        int width = 0;
        int height = 0;
        int i = 0;
        for(;i<count;i++){
            width = getPaddingLeft()+horizontal_spacing*i;
            height = getPaddingTop()+vertical_spacing*i;
            View view = getChildAt(i);
            measureChild(view,widthMeasureSpec,heightMeasureSpec);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.x = width;
            layoutParams.y = height;
            width += view.getMeasuredWidth();
            height+= view.getMeasuredHeight();

        }
        width+= getPaddingRight();
        height += getPaddingBottom();
        setMeasuredDimension(resolveSize(width, widthMeasureSpec),resolveSize(height,heightMeasureSpec));
    }
    public static class LayoutParams extends ViewGroup.LayoutParams{
        public int x;
        public int y;
        public int vertical_spacing;


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs,R.styleable.CascadeLayout_LayoutParams);
            vertical_spacing = a.getDimensionPixelSize(R.styleable.CascadeLayout_LayoutParams_vertical_spacing,-1);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
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
        return new LayoutParams(p.width,p.height);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int count = getChildCount();
        for(int index= 0;index<count;index++){
            View child = getChildAt(index);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            child.layout(layoutParams.x, layoutParams.y,layoutParams.x+child.getMeasuredWidth(),layoutParams.y+child.getMeasuredHeight());
        }
    }
}
