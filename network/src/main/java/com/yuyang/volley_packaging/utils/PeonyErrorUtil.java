package com.yuyang.volley_packaging.utils;

import com.yuyang.volley_packaging.base.PeonyError;

/**
 * 错误类型处理
 */
public class PeonyErrorUtil {
    public static String getVolleyErrorMessage(PeonyError error) {
        if (error == null) return "";
        switch (error.getErrorType()) {
            case PeonyError.ERROR_NO_CONNECTION:
                return "没有网络,请打开网络连接";
            case PeonyError.ERROR_NETWORK:
                return "网络连接错误";
            case PeonyError.ERROR_TIMEOUT:
                return "网络超时";
            case PeonyError.ERROR_SERVER:
                return "服务器异常,请稍后重试";
            case PeonyError.ERROR_PARSE:
                return "数据反序列化错误";
            default:
                return error.getErrorMessage();
        }
    }
}
