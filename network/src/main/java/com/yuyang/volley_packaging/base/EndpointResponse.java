package com.yuyang.volley_packaging.base;

/**
 * 返回基类
 */

public abstract class EndpointResponse {
    private int status;//返回状态
    private String message;//非正常状态下的错误信息

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
