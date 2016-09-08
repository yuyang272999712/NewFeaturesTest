package com.yuyang.fitsystemwindowstestdrawer.mvp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.mvp.BaseMvpActivity;
import com.yuyang.fitsystemwindowstestdrawer.mvp.bean.UserInfo;
import com.yuyang.fitsystemwindowstestdrawer.mvp.presenter.UserInfoPresenter;

public class UserInfoActivity extends BaseMvpActivity<IUserInfoView, UserInfoPresenter> implements IUserInfoView,View.OnClickListener {
    private EditText mUserName;
    private EditText mUserAge;
    private EditText mUserInfo;
    private TextView mUserInfoShow;
    private Button mSave;
    private Button mGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo_mvp);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mUserName = (EditText) findViewById(R.id.user_name);
        mUserAge = (EditText) findViewById(R.id.user_age);
        mUserInfo = (EditText) findViewById(R.id.user_info);
        mUserInfoShow = (TextView) findViewById(R.id.userInfo);
        mSave = (Button) findViewById(R.id.saveUserInfo);
        mGet = (Button) findViewById(R.id.getUserInfo);

        mSave.setOnClickListener(this);
        mGet.setOnClickListener(this);
    }

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    public void setUserInfo(UserInfo userInfo) {
        StringBuilder str = new StringBuilder();
        str.append(userInfo.getName()).append("\n");
        str.append(userInfo.getAge()).append("\n");
        str.append(userInfo.getInfo());
        mUserInfoShow.setText(str.toString());
    }

    @Override
    public UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(mUserName.getText().toString());
        userInfo.setAge(Integer.parseInt(mUserAge.getText().toString()));
        userInfo.setInfo(mUserInfo.getText().toString());
        return userInfo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveUserInfo:
                mPresenter.saveUserInfo(this);
                break;
            case R.id.getUserInfo:
                mPresenter.getUserInfo(this);
                break;
        }
    }
}
