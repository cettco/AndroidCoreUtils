package com.cettco.coreutils.login.logic;

import android.os.Handler;
import android.os.Looper;

import com.cettco.coreutils.login.activity.LoginActivity;
import com.cettco.coreutils.login.bean.UserBean;
import com.cettco.coreutils.utils.IntentUtil;

import java.util.Observable;

/**
 * Created by cettco on 7/18/15.
 */
public class LoginLogic extends Observable{
    private UserBean userBean;
    private LoginCallBack mCallBack;
    private LoginListener loginListener;
    private Handler uiHandler = new Handler(Looper.myLooper());
    public static class LoginCallBack{
        public void callBack(){

        }
    }
    public static interface LoginListener{
        public void onLoginSuccess();
        public void onLoginFail();
    }
    public void login(){
        login(null);
    }
    public void login(LoginCallBack loginCallBack){
        this.mCallBack = loginCallBack;
        IntentUtil.jumpToActivity(LoginActivity.class);

    }
    public void loginByPwd(String account,String pwd, LoginListener loginListener){
        if(checkPhoneAndPwd(account,pwd)){
            this.loginListener = loginListener;
            doLogin(account,pwd);
        }
    }
    public boolean isLogin(){
        //TODO(cettco)
        return true;
    }
    public void logout(){
        logout(null);
    }
    public void logout(LoginCallBack logoutCallBack){
        this.mCallBack = logoutCallBack;
        //TODO(cettco)
        clearInformation();
        //NotifyObservers.
    }
    public void clearInformation(){

    }
    private boolean checkPhoneAndPwd(String account, String pwd){
        return true;
    }
    private void doLogin(String account,String pwd){
        //TODO(cettco) Using volley
        //loginListener.onLoginSuccess();
        //notifyObservers();
        //save to sharepreference

    }
    //TODO(cettco)
    private void parseJson2(){

    }
    public UserBean getUserInfo(){
        return userBean;
    }
    public void autoLogin(){

    }
}
