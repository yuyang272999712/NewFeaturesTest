package com.yuyang.network.volley.baseView;

public class ServiceConfig {
    /***
     * 普通用户模式的服务地址
     */
    private static String IP = "";

    private static Integer PORT;

    private static String URL_PRE = "";

    public static void initIp(String ip, Integer port) {
        IP = ip;
        PORT = port;
        URL_PRE = "http://" + IP + ":" + PORT + "/";
    }

    public static Integer getPort() {
        return PORT;
    }

    public static String getIp() {
        return IP;
    }

    public static String getUrl() {
        return URL_PRE;
    }

}
