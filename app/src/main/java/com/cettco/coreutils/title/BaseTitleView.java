package com.cettco.coreutils.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.cettco.coreutils.R;

/**
 * Created by cettco on 7/18/15.
 */
public abstract class BaseTitleView extends AbstractTitleView {
    protected LinearLayout leftLly;
    protected LinearLayout rightLly;
    protected LinearLayout middleLly;

    @Override
    public void setLeftVisible(boolean visible) {
        leftLly.setVisibility(visible==true? View.VISIBLE:View.GONE);
    }

    @Override
    public void setMiddleVisible(boolean visible) {
        middleLly.setVisibility(visible==true? View.VISIBLE:View.GONE);
    }

    @Override
    public void setRightVisible(boolean visible) {
        rightLly.setVisibility(visible==true? View.VISIBLE:View.GONE);
    }

    public BaseTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.base_title_view, this);
        leftLly = (LinearLayout)findViewById(R.id.title_lly);
        middleLly = (LinearLayout)findViewById(R.id.title_mly);
        rightLly = (LinearLayout) findViewById(R.id.title_rly);
    }
}
