package com.cettco.coreutils.utils;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GlobalThreadPool{
    private static int SIZE = 3;
    private static int MAX_SIZE = 5;
    private static long keepAlive = 3;
    private static ThreadPoolExecutor executor;
    public static ThreadPoolExecutor getThreadPoolInstance(){
        if(executor==null){
            synchronized (GlobalThreadPool.class){
                if(executor==null){
                    executor = new ThreadPoolExecutor(SIZE,MAX_SIZE,keepAlive, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());
                }
            }
        }
        return executor;
    }
    public static void startThread(Runnable runnable){
        getThreadPoolInstance().execute(runnable);
    }
    public static void shutdown(){
        if(executor!=null){
            executor.shutdown();
            executor = null;
        }
    }
}