package com.yuyang.fitsystemwindowstestdrawer.internetAbout.okHttpAbout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * OkHttp使用
 */
public class OkHttpAboutActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_about);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * post请求
     * @param view
     */
    public void postRequest(View view){
        Map<String, String> params = new HashMap<>();
        params.put("wd","测试");
        OkHttpUtils.postParams("http://www.baidu.com/s", params, new CallbackBase<String>() {
            @Override
            public String parseNetworkResponse(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(OkHttpAboutActivity.this, "请求失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Toast.makeText(OkHttpAboutActivity.this, response, Toast.LENGTH_LONG).show();
            }
        });
    }
}
