package com.yuyang.fitsystemwindowstestdrawer.mvp.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.yuyang.fitsystemwindowstestdrawer.mvp.bean.UserInfo;

/**
 * Created by yuyang on 16/3/1.
 */
public class UserInfoModelImpl implements IUserInfoModel {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public UserInfoModelImpl(Context context){
        sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    @Override
    public void setUserInfo(UserInfo userInfo) {
        editor.putString("name", userInfo.getName());
        editor.putInt("age", userInfo.getAge());
        editor.putString("info", userInfo.getInfo());
        editor.commit();
    }

    @Override
    public UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo(sp.getString("name", ""), sp.getInt("age", 1), sp.getString("info", ""));
        return userInfo;
    }
}
