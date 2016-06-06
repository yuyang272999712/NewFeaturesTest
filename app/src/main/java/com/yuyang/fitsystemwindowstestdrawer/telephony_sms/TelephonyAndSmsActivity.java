package com.yuyang.fitsystemwindowstestdrawer.telephony_sms;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 电话与短信服务相关
 * 打电话需要的权限：
 *  <uses-permission android:name="android.permission.CALL_PHONE"/>
 * 直接发送短信的权限：
 *  <uses-permission android:name="android.permission.SEND_SMS"/>
 */
public class TelephonyAndSmsActivity extends AppCompatActivity {
    private Button tel10086;
    private Button call10086;
    private Button getPhoneMessage;

    private Button smsSend;
    private Button smsManager;

    //监听短消息的发送状态
    String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";

    //创建sentIntent参数
    PendingIntent sentPI;
    //创建deliveryIntent参数
    PendingIntent deliverPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephony_sms);

        findViews();
        setAction();

        //创建sentIntent参数
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        sentPI = PendingIntent.getBroadcast(getApplicationContext(),0,sentIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        //创建deliveryIntent参数
        Intent deliveryIntent = new Intent(DELIVERED_SMS_ACTION);
        deliverPI = PendingIntent.getBroadcast(getApplicationContext(),0,deliveryIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        //注册短息广播接收结果
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String resultText = "UNKNOW";
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        resultText = "短信发送成功";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        resultText = "普通的传输失败";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        resultText = "电话无线信号被关闭";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        resultText = "PDU(协议数据单元)错误";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        resultText = "没有可用的手机服务";
                        break;
                }
                Toast.makeText(context, resultText, Toast.LENGTH_LONG).show();
            }
        }, new IntentFilter(SENT_SMS_ACTION));
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context,"对方接收成功",Toast.LENGTH_LONG).show();
            }
        },new IntentFilter(DELIVERED_SMS_ACTION));
    }

    private void findViews() {
        tel10086 = (Button) findViewById(R.id.tel_sms_10086);
        call10086 = (Button) findViewById(R.id.tel_sms_call_10086);
        getPhoneMessage = (Button) findViewById(R.id.tel_sms_phone_message);

        smsSend= (Button) findViewById(R.id.tel_sms_send);
        smsManager = (Button) findViewById(R.id.tel_sms_sms_manager);
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
        getPhoneMessage.setOnClickListener(new View.OnClickListener() {
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

        smsSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:10086"));
                intent.putExtra("sms_body", "10086测试短信");
                startActivity(intent);
            }
        });
        smsManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                String sendTo = "10086";
                String myMessage = "10086测试短信，由应用直接发送";
                /**
                 * 第二个参数可以指定要使用的SMS服务中心，null将使用运营商默认的服务中心
                 * 最后两个参数制定了用于追踪传输 和 消息成功递送的Intent。为了响应这些Intent，创建并注册
                 * Broadcast Receiver,第一个Intent会在消息发送成功或失败时触发，第二个Intent会在接收人
                 * 接收到你的SMS消息之后触发,两个参数可以为空
                 */
                smsManager.sendTextMessage(sendTo, null, myMessage, sentPI, deliverPI);
            }
        });
    }
}
