package com.yuyang.fitsystemwindowstestdrawer.mvp.activity;

import com.yuyang.fitsystemwindowstestdrawer.mvp.bean.UserInfo;

/**
 * Created by yuyang on 16/3/1.
 */
public interface IUserInfoView {

    void setUserInfo(UserInfo userInfo);

    UserInfo getUserInfo();
}
