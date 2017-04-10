package com.yuyang.volley_packaging.base;

import java.util.HashMap;

/**
 * 请求基类
 */

public interface EndpointRequest {
    HashMap<String, String> getHeaders();//获取公共的请求头参数
    String getProtocol();//请求协议：http／https
    String getHost();//地址：www.baidu.com
    int getPort();//端口号
    String getPath();//路径
    boolean isShowJSON();//是否打印json
    boolean isShowLoading();//是否显示加载动画
}
