/*******************************************************************************
 * * Copyright (C) 2015 www.wkzf.com
 * *
 * * Licensed under the Apache License, Version 2.0 (the "License");
 * * you may not use this file except in compliance with the License.
 * * You may obtain a copy of the License at
 * *
 * *      http://www.apache.org/licenses/LICENSE-2.0
 * *
 * * Unless required by applicable law or agreed to in writing, software
 * * distributed under the License is distributed on an "AS IS" BASIS,
 * * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * * See the License for the specific language governing permissions and
 * * limitations under the License.
 ******************************************************************************/

package com.yuyang.network.volley.netWork.base;

import com.google.gson.annotations.Expose;
import com.yuyang.network.volley.baseView.AppConfig;
import com.yuyang.network.volley.baseView.ServiceConfig;
import com.yuyang.volley_packaging.base.EndpointRequest;

import java.util.HashMap;

public class LFBaseRequest implements EndpointRequest {

    @Expose
    private String token;

    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return AppConfig.getHeaders();
    }

    @Override
    public String getProtocol() {
        return "https";
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
    public boolean isShowJSON() {
        return AppConfig.isShowJson();
    }

    @Override
    public String getPath() {
        return "";
    }

    public boolean isShowLoading() {
        return false;
    }

}
