package com.cettco.coreutils.utils;

import android.content.Intent;

/**
 * Created by cettco on 7/31/15.
 */
public class IntentUtil {
    public static void jumpToActivity(Class cls){
        Intent intent = new Intent(ContextProvider.getApplicationContext(),cls);
        ContextProvider.getApplicationContext().startActivity(intent);
    }
}
