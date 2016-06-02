package com.yuyang.fitsystemwindowstestdrawer.telephony_sms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 电话与短信服务相关
 */
public class TelephonyAndSmsActivity extends AppCompatActivity {
    private Button tel10086;
    private Button call10086;
    private Button getPhoneMeaage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephony_sms);

        findViews();
        setAction();
    }

    private void findViews() {
        tel10086 = (Button) findViewById(R.id.tel_sms_10086);
        call10086 = (Button) findViewById(R.id.tel_sms_call_10086);
        getPhoneMeaage = (Button) findViewById(R.id.tel_sms_phone_message);
    }

    private void setAction() {
        tel10086.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:10086"));
                startActivity(intent);
            }
        });
        call10086.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10086"));
                //检查是否有呼叫权限
                if (ActivityCompat.checkSelfPermission(TelephonyAndSmsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                    return;
                }
            }
        });
        getPhoneMeaage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO yuyang 获取本机信息
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                TextView textView = (TextView) findViewById(R.id.tel_sms_message);
                textView.setText("IMEI："+telephonyManager.getDeviceId()+"；\n"+
                        "软件版本："+telephonyManager.getDeviceSoftwareVersion()+"；\n"+
                        "本机号码："+telephonyManager.getLine1Number()+"；\n"+
                        "SIM卡序列号："+telephonyManager.getSimSerialNumber()+"；\n");
            }
        });
    }
}
