package com.yuyang.network.volley.baseView;

import com.google.gson.annotations.Expose;
import com.yuyang.volley_packaging.base.EndpointRequest;

import java.util.HashMap;

/**
 * Request基础类
 */
public class LdBaseRequest implements EndpointRequest {

    @Expose
    private boolean isShowLoading = true;

    @Override
    public String getProtocol() {
        return "https";//!--yuyang 这里我用的是github的接口，是https的
    }

    @Override
    public String getHost() {
        return ServiceConfig.getIp();
    }

    @Override
    public int getPort() {
        return ServiceConfig.getPort();
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return AppConfig.getHeaders();
    }

    @Override
    public boolean isShowJSON() {
        return AppConfig.isShowJson();
    }

    @Override
    public boolean isShowLoading() {
        return isShowLoading;
    }

    public void setShowLoading(boolean isShowLoading) {
        this.isShowLoading = isShowLoading;
    }
}