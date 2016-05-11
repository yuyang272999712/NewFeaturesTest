package com.yuyang.fitsystemwindowstestdrawer.webview;

/**
 * Created by yuyang on 16/5/6.
 */
public class LoginBean {
    String userName;
    String password;

    public LoginBean(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "userName:"+userName+"; password:"+password;
    }

    public LoginBean() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
