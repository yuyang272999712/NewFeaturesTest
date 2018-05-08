package com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.ConverterFactory;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.Gson;
import com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.UserInfoBean;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 自定义ResponseBodyConverter（相当于Retrofit自带的ResponseBodyConverter）
 */
public class UserInfoResponseBodyConverter implements Converter<ResponseBody, UserInfoBean> {

    public static final UserInfoResponseBodyConverter INSTANCE = new UserInfoResponseBodyConverter();

    @SuppressLint("LongLogTag")
    @Override
    public UserInfoBean convert(ResponseBody value) throws IOException {
        Log.e("UserInfoResponseBodyConverter", "自定义转化器，进行UserInfoBean的json序列化");
        UserInfoBean result = new Gson().fromJson(value.string(), UserInfoBean.class);
        return result;
    }
}
