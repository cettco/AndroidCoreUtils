package com.cettco.coreutils.widget;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cettco on 8/3/15.
 */
public class Cascade2Layout extends ViewGroup{

    private int ANIMATION_DELAY = 200;
    private int normal_width=460;
    private int normal_height=460;
    private int normal_width_increase_value=20;
    private int normal_vertical_margin=20;

    private static int NORMAL_MODE = 0;
    private static int SELECTED_MODE = 1;
    private int mode = NORMAL_MODE;
    private int horizontal_block_margain=20;
    private int vertical_block_margain=20;
    private boolean hasInitAnimator = false;
    private List<ObjectAnimator> animators;
    public Cascade2Layout(Context context) {
        super(context);
    }

    public Cascade2Layout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable);
//        a.getDimensionPixelSize(R.styleable,R.dimen)；
//        a.recycle();
    }

    public Cascade2Layout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for(int i = 0;i<count;i++){
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams)child.getLayoutParams();
            child.layout(lp.x, lp.y, lp.x + child.getMeasuredWidth(), lp.y + child.getMeasuredHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        int layoutHeight = MeasureSpec.getSize(heightMeasureSpec);
        int count = getChildCount();
        for(int i = count-1;i>=0;i--){
            View child = getChildAt(i);
            child.setPivotY(0);
            child.setPivotX(0);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            LayoutParams lp = (LayoutParams)child.getLayoutParams();
            lp.width = normal_width-normal_width_increase_value*i;
            lp.height = normal_height;
            lp.x = layoutWidth/2-lp.width/2;
            lp.y = normal_vertical_margin*(count-1-i);
        }
        setMeasuredDimension(layoutWidth,layoutHeight);
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

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public static class LayoutParams extends LinearLayout.LayoutParams{
        private int x;
        private int y;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }
    }
    public void toggleState(){
        mode = mode == NORMAL_MODE?SELECTED_MODE:NORMAL_MODE;
        startAnimation();
    }
    private void startAnimation(){
        if(!hasInitAnimator){
            initAnimator();
        }
        if(animators==null) return;
        if(mode==SELECTED_MODE){
            for(ObjectAnimator animator:animators){
                animator.start();
            }
        }else{
            for(ObjectAnimator animator:animators){
                animator.reverse();
            }
        }
    }
    private void initAnimator(){
        animators = new ArrayList<ObjectAnimator>();
        int block_width = (getWidth()-3*horizontal_block_margain)/2;
        int block_height = (getHeight()-3*vertical_block_margain)/2;
        int count = getChildCount();
        int x=horizontal_block_margain;
        int y = vertical_block_margain;
        for(int i=count-1;i>=0;i--){
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams)child.getLayoutParams();
            int transX = x-lp.x;
            int transY = y-lp.y;
            float scaleX = (float)block_width/lp.width;
            float scaleY = (float)block_height/lp.height;
            initAnimator(child,transX,transY,scaleX,scaleY);
            if(i%2==1){
                x +=block_width+horizontal_block_margain;
            }else{
                x=horizontal_block_margain;
                y += block_height+vertical_block_margain;
            }
        }
    }
    private void initAnimator(View view,int transX, int transY, float scaleX, float scaleY){
        view.setPivotY(0);
        view.setPivotX(0);
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("translationX", 0, transX);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("translationY", 0, transY);
        PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, scaleX);
        PropertyValuesHolder holder4 = PropertyValuesHolder.ofFloat("scaleY", 1.0f, scaleY);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, holder1, holder2, holder3, holder4);
        animator.setDuration(ANIMATION_DELAY);
        animators.add(animator);
    }
}
