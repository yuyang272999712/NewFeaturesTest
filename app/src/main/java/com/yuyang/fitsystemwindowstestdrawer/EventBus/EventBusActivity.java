package com.yuyang.fitsystemwindowstestdrawer.eventBus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.UserInfoBean;
import com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.UserService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * EventBus简单使用
 */
public class EventBusActivity extends EventBusBaseActivity {
    private Toolbar toolbar;
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textView = (TextView) findViewById(R.id.user_info);
        button = (Button) findViewById(R.id.request_user_info);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.github.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserService service = retrofit.create(UserService.class);
                service.getUserInfo("yuyang").enqueue(new Callback<UserInfoBean>() {
                    @Override
                    public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
                        EventBus.getDefault().post(response.body());
                    }

                    @Override
                    public void onFailure(Call<UserInfoBean> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1, sticky = false)
    public void setUserInfo(UserInfoBean userInfo){
        textView.setText(userInfo.toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void test(UserInfoBean userInfo){
        Toast.makeText(this, "测试", Toast.LENGTH_SHORT).show();
    }
}
