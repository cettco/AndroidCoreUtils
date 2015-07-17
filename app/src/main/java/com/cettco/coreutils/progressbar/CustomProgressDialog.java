package com.cettco.coreutils.progressbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cettco.coreutils.R;

/**
 * Created by cettco on 7/17/15.
 */
public class CustomProgressDialog extends ProgressDialog {
    private String mContent;
    private int mId;
    private ImageView loadingIv;
    private TextView loadingTv;
    private AnimationDrawable animationDrawable;
    public CustomProgressDialog(Context context, String content, int id) {
        super(context);
        mContent = content;
        mId = id;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        initView();
        initData();
    }
    private void initView(){
        setContentView(R.layout.progress_dialog);
        loadingIv = (ImageView)findViewById(R.id.loadingIv);
        loadingTv = (TextView)findViewById(R.id.loadingTv);
    }
    private void initData(){
        loadingIv.setBackgroundResource(mId);
        animationDrawable = (AnimationDrawable)loadingIv.getBackground();
        loadingIv.post(new Runnable() {
            @Override
            public void run() {
                animationDrawable.start();
            }
        });
        loadingTv.setText(mContent);
    }
}
