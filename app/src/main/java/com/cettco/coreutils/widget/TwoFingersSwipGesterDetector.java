package com.cettco.coreutils.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/**
 * Created by cettco on 8/11/15.
 */
public class TwoFingersSwipGesterDetector{
    public interface OnGestureListener {
        public boolean onFling();
    }

    private VelocityTracker mVelocityTracker;
    private OnGestureListener mListener;
    private int mMinimumFlingVelocity;
    private int mMaximumFlingVelocity;
    private int count = 0;
    private final int fingerNums = 2;

    public TwoFingersSwipGesterDetector(Context context, OnGestureListener mListener) {
        this.mListener = mListener;
        init(context);
    }

    private void init(Context context) {
        if (mListener == null) {
            throw new NullPointerException("OnGestureListener must not be null");
        }
        // Fallback to support pre-donuts releases
        int touchSlop, doubleTapSlop, doubleTapTouchSlop;
        if (context == null) {
            //noinspection deprecation
            touchSlop = ViewConfiguration.getTouchSlop();
            mMinimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
            mMaximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
        } else {
            final ViewConfiguration configuration = ViewConfiguration.get(context);
            mMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
            mMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
        }
    }
    public boolean onTouch(MotionEvent motionEvent) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(motionEvent);
        boolean handled = false;
        final int action = motionEvent.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                count = 1;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                count++;
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("count:"+count);
                if (count != fingerNums) break;
                // A fling must travel the minimum tap distance
                final VelocityTracker velocityTracker = mVelocityTracker;
                final int pointerId = motionEvent.getPointerId(0);
                velocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
                final float velocityY = velocityTracker.getYVelocity(pointerId);
                if ((Math.abs(velocityY) > mMinimumFlingVelocity)) {
                    handled = mListener.onFling();
                }else{
                    System.out.println("not fling");
                }
                if (mVelocityTracker != null) {
                    // This may have been cleared when we called out to the
                    // application above.
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
        }
        return handled;
    }
}
