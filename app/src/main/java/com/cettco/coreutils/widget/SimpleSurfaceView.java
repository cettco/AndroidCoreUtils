package com.cettco.coreutils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by cettco on 8/29/15.
 */
public class SimpleSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private SurfaceHolder holder;
    private Mthread mthread;
    public SimpleSurfaceView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
        mthread = new Mthread(holder);
    }

    public SimpleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mthread.isRun = true;
        mthread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mthread.isRun = false;
    }
    private class Mthread extends Thread{
        private SurfaceHolder holder;
        public Mthread(SurfaceHolder holder){
            this.holder = holder;
        }
        public boolean isRun = true;
        @Override
        public void run() {
            int count = 0;
            while (isRun){
                Canvas c = null;
                synchronized (holder){
                    c = holder.lockCanvas();
                    c.drawColor(Color.BLACK);//设置画布背景颜色
                    Paint p = new Paint(); //创建画笔
                    p.setColor(Color.WHITE);
                    Rect r = new Rect(100, 50, 300, 250);
                    c.drawRect(r, p);
                    c.drawText((count++)+"s", 100, 310, p);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(c!=null){
                    holder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}
