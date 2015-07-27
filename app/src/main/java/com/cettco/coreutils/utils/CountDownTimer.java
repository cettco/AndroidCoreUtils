package com.cettco.coreutils.utils;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by cettco on 7/27/15.
 */
public class CountDownTimer {
    private final int delay = 1000;
    private int time;
    private TextView mView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if(time>0){
                        if(mView!=null){
                            mView.setText(""+(--time));
                            Message message = handler.obtainMessage(1);
                            handler.sendMessageDelayed(message,delay);
                        }
                    }
            }
        }
    };
    public void setView(TextView view){
        this.mView = view;
    }
    public void clearView(){
        this.mView = null;
    }
    public void start(int time){
        if(mView==null||time<=0){
            return;
        }
        mView.setText(""+time);
        this.time = time;
        Message message = handler.obtainMessage(1);
        handler.sendMessageDelayed(message,delay);
    }
}
