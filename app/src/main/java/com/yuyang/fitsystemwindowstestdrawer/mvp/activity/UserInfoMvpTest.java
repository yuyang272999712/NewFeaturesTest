package com.yuyang.fitsystemwindowstestdrawer.mvp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.mvp.bean.UserInfo;
import com.yuyang.fitsystemwindowstestdrawer.mvp.presenter.UserInfoPresenter;

/**
 * Created by yuyang on 16/3/1.
 */
public class UserInfoMvpTest extends AppCompatActivity implements IUserInfoMvp,View.OnClickListener {
    private EditText mUserName;
    private EditText mUserAge;
    private EditText mUserInfo;
    private TextView mUserInfoShow;
    private Button mSave;
    private Button mGet;

    private UserInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.userinfo);

        presenter = new UserInfoPresenter(this, this);
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
                presenter.saveUserInfo();
                break;
            case R.id.getUserInfo:
                presenter.setUserInfo();
                break;
        }
    }
}
