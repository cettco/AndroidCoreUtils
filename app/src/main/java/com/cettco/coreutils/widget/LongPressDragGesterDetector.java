package com.cettco.coreutils.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by cettco on 8/12/15.
 */
public class LongPressDragGesterDetector {
    private Handler mHandler = new Handler();
    private View topView;
    private ImageView mDragImageView;
    private Context mContext;
    private Vibrator mVibrator;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowLayoutParams;
    private Bitmap mDragBitmap;
    private View mStartDragItemView;

    private int mDownX;
    private int mDownY;
    private long dragResponseMS = 1000;

    private boolean isDrag = false;

    /**
     * 按下的点到所在item的上边缘的距离
     */
    private int mPoint2ItemTop ;

    /**
     * 按下的点到所在item的左边缘的距离
     */
    private int mPoint2ItemLeft;


    //用来处理是否为长按的Runnable
    private Runnable mLongClickRunnable = new Runnable() {

        @Override
        public void run() {
            isDrag = true; //设置可以拖拽
            mVibrator.vibrate(50); //震动一下

            //根据我们按下的点显示item镜像
            createDragImage(mDragBitmap, mDownX, mDownY);
        }
    };
    public LongPressDragGesterDetector(Context context) {
        init(context);
    }
    private void init(Context context){
        this.mContext = context;
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    }
    public void setTopView(View view){
        this.topView = view;
    }
    public boolean onTouch(View view, MotionEvent event){
        mStartDragItemView = view;
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getRawX();
                mDownY = (int) event.getRawY();
                mHandler.postDelayed(mLongClickRunnable, dragResponseMS);

                mPoint2ItemLeft = (int)(mDownX - mStartDragItemView.getX());
                mPoint2ItemTop = (int)(mDownY - mStartDragItemView.getY());
                //开启mDragItemView绘图缓存
                mStartDragItemView.setDrawingCacheEnabled(true);
                //获取mDragItemView在缓存中的Bitmap对象
                mDragBitmap = Bitmap.createBitmap(mStartDragItemView.getDrawingCache());
                //这一步很关键，释放绘图缓存，避免出现重复的镜像
                mStartDragItemView.destroyDrawingCache();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int)event.getX();
                int moveY = (int) event.getY();
                int moveRowX = (int)event.getRawX();
                int moveRowY = (int) event.getRawY();

                //如果我们在按下的item上面移动，只要不超过item的边界我们就不移除mRunnable
                if(!isTouchInItem(mStartDragItemView, moveX, moveY)){
                    mHandler.removeCallbacks(mLongClickRunnable);
                }
                if(isDrag&&mDragImageView!=null){
                    onDragItem(moveRowX, moveRowY);
                }
                break;
            case MotionEvent.ACTION_UP:
                removeDragImage();
                isDrag = false;
                mHandler.removeCallbacks(mLongClickRunnable);
                break;
        }
        return false;
    }
    private void createDragImage(Bitmap bitmap, int downX , int downY){
        mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams.format = PixelFormat.TRANSLUCENT;
        mWindowLayoutParams.gravity = Gravity.TOP|Gravity.LEFT ;
        mWindowLayoutParams.x = downX-mPoint2ItemLeft ;
        mWindowLayoutParams.y = downY-mPoint2ItemTop;
        mWindowLayoutParams.alpha = 0.55f; //透明度
        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        mWindowLayoutParams.width = mStartDragItemView.getWidth()+10;
//        mWindowLayoutParams.height = mStartDragItemView.getHeight()+10;
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE ;
        mWindowLayoutParams.type=WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;

        mDragImageView = new ImageView(this.mContext);
        mDragImageView.setImageBitmap(bitmap);
        //mDragImageView.setBackgroundResource(android.R.color.background_dark);
        mWindowManager.addView(mDragImageView, mWindowLayoutParams);
    }
    private void removeDragImage(){
        if(mDragImageView != null){
            mWindowManager.removeView(mDragImageView);
            mDragImageView = null;
        }
    }
    private void onDragItem(int moveX, int moveY){
        mWindowLayoutParams.x = moveX -mPoint2ItemLeft;
        mWindowLayoutParams.y = moveY-mPoint2ItemTop;
        mWindowManager.updateViewLayout(mDragImageView, mWindowLayoutParams);
    }
    private boolean isTouchInItem(View dragView, int x, int y){
        if(dragView == null){
            return false;
        }
        int leftOffset = dragView.getLeft();
        int topOffset = dragView.getTop();
        if(x < leftOffset || x > leftOffset + dragView.getWidth()){
            return false;
        }

        if(y < topOffset || y > topOffset + dragView.getHeight()){
            return false;
        }

        return true;
    }
}
