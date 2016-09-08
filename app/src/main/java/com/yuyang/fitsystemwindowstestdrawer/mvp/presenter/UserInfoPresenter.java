package com.yuyang.fitsystemwindowstestdrawer.mvp.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.yuyang.fitsystemwindowstestdrawer.mvp.BaseMvpPresenter;
import com.yuyang.fitsystemwindowstestdrawer.mvp.activity.IUserInfoView;
import com.yuyang.fitsystemwindowstestdrawer.mvp.model.IUserInfoModel;
import com.yuyang.fitsystemwindowstestdrawer.mvp.model.UserInfoModelImpl;

public class UserInfoPresenter extends BaseMvpPresenter<IUserInfoView> {

    //供UI调运
    public void saveUserInfo(Context context){
        IUserInfoModel iUserInfoModel = new UserInfoModelImpl(context);
        iUserInfoModel.setUserInfo(getView().getUserInfo());
    }

    //供UI调运
    public void getUserInfo(Context context){
        final IUserInfoModel iUserInfoModel = new UserInfoModelImpl(context);
        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    //!--yuyang 模拟耗时请求
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                //TODO yuyang 这里要小心Activity已经被销毁 !--yuyang 先判断是否已经绑定了Activity，有可能用户已经推出了该Activity
                if (isViewAttached()) {
                    getView().setUserInfo(iUserInfoModel.getUserInfo());
                }
            }
        };
        asyncTask.execute();
    }
}
