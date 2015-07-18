package com.cettco.coreutils.title;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by cettco on 7/18/15.
 */
abstract class AbstractTitleView extends FrameLayout{
    public AbstractTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public abstract void setLeftVisible(boolean visible);
    public abstract void setRightVisible(boolean visible);
    public abstract void setMiddleVisible(boolean visible);
}
