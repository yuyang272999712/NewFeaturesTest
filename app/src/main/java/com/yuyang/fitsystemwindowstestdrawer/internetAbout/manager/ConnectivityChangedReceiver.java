package com.yuyang.fitsystemwindowstestdrawer.internetAbout.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * 监听网络连接状态变化
 */
public class ConnectivityChangedReceiver extends BroadcastReceiver {
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;
    private static volatile boolean netStates = false;//记录当前网络状态之前的网络状态 （false-之前网络中断 true-之前网络已连接）
    private static volatile boolean noNetNotice = false;//避免网络中断重复提示
    private static volatile boolean dataNetNotice = false;//避免使用移动运营商数据重复提示
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == ConnectivityManager.CONNECTIVITY_ACTION){
            Toast.makeText(context, "网络连接发生了变化", Toast.LENGTH_SHORT).show();
            connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = connectivityManager.getActiveNetworkInfo();
            if(info != null && info.isAvailable()) {
                String name = info.getTypeName();
                Log.e("--yuyang--","#当前网络名称：" + name);
                if(!netStates){
                    netStates = true;
                    Toast.makeText(context, "网络连接已恢复。", Toast.LENGTH_LONG).show();
                }
                if(info.getType() != ConnectivityManager.TYPE_WIFI && !dataNetNotice){
                    dataNetNotice = true;
                    Toast.makeText(context, "您正在使用移动数据流量。", Toast.LENGTH_LONG).show();
                }else{
                    dataNetNotice = false;
                }
                noNetNotice = false;
            } else {
                netStates = false;
                if(!noNetNotice){
                    noNetNotice = true;
                    Toast.makeText(context, "无网络可用", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
