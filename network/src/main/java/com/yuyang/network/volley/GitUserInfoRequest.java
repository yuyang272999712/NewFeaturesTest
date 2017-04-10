package com.yuyang.network.volley;

import com.yuyang.network.volley.netWork.base.LFBaseRequest;
import com.yuyang.volley_packaging.annotation.RequestAnnotation;
import com.yuyang.network.volley.baseView.LdBaseRequest;

/**
 * Created by yuyang on 2017/4/10.
 */
@RequestAnnotation(path = "/users/yuyang")
public class GitUserInfoRequest extends LFBaseRequest {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
