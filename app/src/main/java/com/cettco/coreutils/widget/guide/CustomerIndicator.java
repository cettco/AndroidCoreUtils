package com.cettco.coreutils.widget.guide;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cettco.coreutils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cettco on 7/28/15.
 */
public class CustomerIndicator extends LinearLayout {
    private int currentPosition = 0;
    private int count = 0;
    private Context mContext;
    private List<ImageView> list = new ArrayList<ImageView>();
    private int width=0;
    private int height = 0;
    private int normalId;
    private int selectedId;
    private int rightMargin=0;
    public CustomerIndicator(Context context) {
        super(context);
        init(context);
    }

    public CustomerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    private void init(Context context){
        this.mContext = context;
        rightMargin = 10;
        width = 40;
        height = 40;
        count = 0;
        this.normalId = R.drawable.icon_indicator_0;
        this.selectedId = R.drawable.icon_indicator_1;
    }
    public void initParams(int width,int height,int rightMargin){
        this.width = width;
        this.height = height;
        this.rightMargin = rightMargin;
    }
    public void initResourceId(int normalId,int selectedId){
        this.normalId = normalId;
        this.selectedId = selectedId;
    }
    public void setPosition(int position){
        list.get(currentPosition).setBackgroundResource(normalId);
        currentPosition = position;
        list.get(currentPosition).setBackgroundResource(selectedId);

    }
    public void setCount(int count){
        this.count = count;
    }
    public void show(){
        initViews();
    }
    private void initViews(){
        list.clear();
        for(int i=0;i<this.count;i++){
            ImageView imageView = new ImageView(this.mContext);
            LayoutParams lp = new LayoutParams(width==0?LayoutParams.WRAP_CONTENT:width,height==0?LayoutParams.WRAP_CONTENT:height);
            if(i!=count-1){
                lp.rightMargin = rightMargin;
            }
            imageView.setLayoutParams(lp);
            imageView.setBackgroundResource(normalId);
            list.add(imageView);
            this.addView(imageView);
        }
        setPosition(0);
    }
}
