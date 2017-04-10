package com.yuyang.network.volley.baseView;

import java.util.HashMap;

/**
 * Created by yuyang on 2017/4/10.
 */

public class AppConfig {
    private static AppConfig instance = new AppConfig();

    private static int versionCode = 1;
    private static String versionName = "1.0";
    private static boolean isShowJson = true;

    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }

    public static int getVersionCode() {
        return versionCode;
    }

    public static void setVersionCode(int versionCode) {
        AppConfig.versionCode = versionCode;
    }

    public static String getVersionName() {
        return versionName;
    }

    public static void setVersionName(String versionName) {
        AppConfig.versionName = versionName;
    }

    public static boolean isShowJson() {
        return isShowJson;
    }

    public static void setIsShowJson(boolean isShowJson) {
        AppConfig.isShowJson = isShowJson;
    }

    public static HashMap<String, String> getHeaders() {
        HashMap<String, String> header = new HashMap<>();
        header.put("Accept", "application/json; charset=UTF-8");
        header.put("Accept-Encoding", "gzip, deflate");
        header.put("os", "android");
        header.put("version", AppConfig.getVersionName());
        header.put("channel", AppConfig.getVersionCode()+"");
        //header.put("token", UserInfoOps.getLoginToken());
        return header;
    }
}
