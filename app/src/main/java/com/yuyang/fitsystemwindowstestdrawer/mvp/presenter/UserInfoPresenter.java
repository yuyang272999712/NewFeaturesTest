package com.yuyang.fitsystemwindowstestdrawer.mvp.presenter;

import android.content.Context;

import com.yuyang.fitsystemwindowstestdrawer.mvp.activity.IUserInfoMvp;
import com.yuyang.fitsystemwindowstestdrawer.mvp.model.IUserInfoModel;
import com.yuyang.fitsystemwindowstestdrawer.mvp.model.UserInfoModelImpl;

/**
 * Created by yuyang on 16/3/1.
 */
public class UserInfoPresenter {
    private IUserInfoMvp iUserInfoMvp;
    private IUserInfoModel iUserInfoModel;

    public UserInfoPresenter(Context context, IUserInfoMvp iuserInfo){
        this.iUserInfoMvp = iuserInfo;
        this.iUserInfoModel = new UserInfoModelImpl(context);
    }

    //供UI调运
    public void saveUserInfo(){
        iUserInfoModel.setUserInfo(iUserInfoMvp.getUserInfo());
    }

    //供UI调运
    public void setUserInfo(){
        iUserInfoMvp.setUserInfo(iUserInfoModel.getUserInfo());
    }
}
