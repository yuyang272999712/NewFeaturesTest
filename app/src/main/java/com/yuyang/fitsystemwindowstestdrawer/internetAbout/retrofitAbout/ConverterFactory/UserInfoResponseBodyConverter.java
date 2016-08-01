package com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.ConverterFactory;

import com.google.gson.Gson;
import com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.UserInfoBean;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 自定义ResponseBodyConverter（相当于Retrofit自带的ResponseBodyConverter）
 */
public class UserInfoResponseBodyConverter implements Converter<ResponseBody, UserInfoBean> {
    @Override
    public UserInfoBean convert(ResponseBody value) throws IOException {
        UserInfoBean result = new Gson().fromJson(value.string(), UserInfoBean.class);
        return result;
    }
}
