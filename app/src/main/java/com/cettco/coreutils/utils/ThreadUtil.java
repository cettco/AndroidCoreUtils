package com.cettco.coreutils.utils;
public class ThreadUtil{
    public static void startThreadInTreadPool(Runnable runnable){
        GlobalThreadPool.startThread(runnable);
    }
    public static void startSingleThread(Runnable runnable){
        new Thread(runnable).start();
    }
}