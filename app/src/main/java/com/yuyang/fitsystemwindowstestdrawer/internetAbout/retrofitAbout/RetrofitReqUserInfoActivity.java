package com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.UserInfoBean;
import com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 使用Retrofit请求数据
 *
 */
public class RetrofitReqUserInfoActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://api.github.com/";

    private Button requestData;
    private TextView userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        findViews();
        setAction();
    }

    private void setAction() {
        requestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO yuyang Retrofit请求全过程
                //1、初始化Retrofit
                Retrofit retrofit = new Retrofit.Builder()
                        //TODO 这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())//解析方法
                        .build();
                //2、创建访问API的请求
                UserService apiService = retrofit.create(UserService.class);//接口申明
                Call<UserInfoBean> call = apiService.getUserInfo("yuyang");
                /*
                TODO 同步请求
                try {
                    call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TODO 取消请求
                call.cancel();
                */
                //3、异步请求
                call.enqueue(new Callback<UserInfoBean>() {
                    @Override
                    public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                        userInfo.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<UserInfoBean> call, Throwable t) {
                        userInfo.setText("获取用户信息失败");
                    }
                });
            }
        });
    }

    private void findViews() {
        requestData = (Button) findViewById(R.id.retrofit_request_data);
        userInfo = (TextView) findViewById(R.id.user_info);
    }
}
