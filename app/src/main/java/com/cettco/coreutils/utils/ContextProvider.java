package com.cettco.coreutils.utils;

import android.content.Context;

/**
 * Created by cettco on 7/31/15.
 */
public class ContextProvider {
    private static Context mContext;
    private ContextProvider(){}
    public static void init(Context context){
        if(context==null){
            throw new NullPointerException("initial failed, the context is null");
        }
        mContext = context;
    }
    public static Context getApplicationContext(){
        if(mContext==null){
            throw new NullPointerException("Can't get context, it's null");
        }
        return mContext;
    }
}
