package com.yuyang.fitsystemwindowstestdrawer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 使用IntentService启动异步服务
 */
public class UploadImgActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout layout;
    private Button uploadBtn;
    public static final String UPLOAD_RESULT = "com.yuyang.service.UPLOAD_RESULT";

    private BroadcastReceiver uploadImgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == UPLOAD_RESULT){
                String path = intent.getStringExtra(UploadImgIntentService.EXTRA_IMG_PATH);
                TextView textView = (TextView) layout.findViewWithTag(path);
                textView.setText(path + " upload success ~~~ ");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_img);

        findViews();
        setAction();

        IntentFilter intentFilter = new IntentFilter(UPLOAD_RESULT);
        registerReceiver(uploadImgReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(uploadImgReceiver);
    }

    private void setAction() {
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            int i = 0;
            @Override
            public void onClick(View v) {
                //模拟路径
                String path = "/sdcard/imgs/" + (++i) + ".png";
                UploadImgIntentService.startUploadImg(UploadImgActivity.this, path);

                TextView tv = new TextView(UploadImgActivity.this);
                layout.addView(tv);
                tv.setText(path + " is uploading ...");
                tv.setTag(path);
            }
        });
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        layout = (LinearLayout) findViewById(R.id.upload_img_layout);
        uploadBtn = (Button) findViewById(R.id.upload_img_btn);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
