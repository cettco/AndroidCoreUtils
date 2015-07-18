package com.cettco.coreutils.title;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by cettco on 7/18/15.
 */
public class CustomTitleView extends BaseTitleView {
    private Button rightBtn;
    private Button leftBtn;
    private TextView titleTv;
    private Context mContext;
    public CustomTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }
    private void initView(){
        leftBtn = new Button(mContext);
        rightBtn = new Button(mContext);
        titleTv = new TextView(mContext);
        leftBtn.setText("Back");
        rightBtn.setText("More");
        titleTv.setText("Title");
        leftLly.addView(leftBtn);
        middleLly.addView(titleTv);
        rightLly.addView(rightBtn);
    }
    public void setLeftBg(){

    }
    public void setRightBg(){

    }
    public void setLeftClickListener(OnClickListener listener){

    }
    public void setRightClickListener(OnClickListener listener){

    }
}
