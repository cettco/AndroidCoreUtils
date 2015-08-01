package com.cettco.coreutils.app;

import android.app.Application;

import com.cettco.coreutils.utils.ContextProvider;

/**
 * Created by cettco on 7/31/15.
 */
public class CoreApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        ContextProvider.init(this);
    }
}
