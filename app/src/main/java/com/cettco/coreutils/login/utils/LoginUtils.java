package com.cettco.coreutils.login.utils;

import com.cettco.coreutils.login.bean.UserBean;
import com.cettco.coreutils.login.logic.LoginLogic;

import java.util.Observer;

/**
 * Created by cettco on 7/18/15.
 */
public class LoginUtils {
    private static LoginLogic loginInstance;
    private LoginUtils(){
    }
    static {
        loginInstance = new LoginLogic();
    }
    public static void login(){
        login(null);
    }
    public static void login(LoginLogic.LoginCallBack loginCallBack){
        loginInstance.login(loginCallBack);

    }
    public static void loginByPwd(String account,String pwd, LoginLogic.LoginListener listener){
        loginInstance.loginByPwd(account,pwd,listener);
    }
    public static boolean isLogin(){
        return loginInstance.isLogin();
    }
    public static UserBean getUserInfo(){
        return loginInstance.getUserInfo();
    }
    public static void logout(){
        logout(null);
    }
    public static void logout(LoginLogic.LoginCallBack logoutCallBack){
        loginInstance.logout(logoutCallBack);

    }
    public static void autoLogin(){
        loginInstance.autoLogin();
    }

    public static void addObserver(Observer observer){
        loginInstance.addObserver(observer);
    }
    public static void deleteObserver(Observer observer){
        loginInstance.deleteObserver(observer);
    }

}
