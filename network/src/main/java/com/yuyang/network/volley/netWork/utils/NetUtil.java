package com.yuyang.network.volley.netWork.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yuyang.MyApplication;

/**
 * Created by youj on 2015/05/11.
 */
public class NetUtil {

    /**
     * 判断是否有网络
     */
    public static boolean checkNetwork() {
        ConnectivityManager conManager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == conManager) {
            return false;
        }

        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        if (null == networkInfo || !networkInfo.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }

}
