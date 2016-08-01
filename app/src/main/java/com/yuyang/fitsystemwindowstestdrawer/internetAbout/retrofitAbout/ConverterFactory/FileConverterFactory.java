package com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.ConverterFactory;

import com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.UserInfoBean;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 自定义入参／回参处理方法
 *  可参考Retrofit定义好的GsonConverterFactory.class使用Gson解析实体类的方法
 * TODO yuyang 该类就相当于
 *  new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
 *  addConverterFactory()中的参数
 */
public class FileConverterFactory extends Converter.Factory {
    //入参（请求参数）格式处理方法
    @Override
    public Converter<File, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new FileRequestBodyConverter();
    }

    //回参（返回参数）格式处理方法
    @Override
    public Converter<ResponseBody, UserInfoBean> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new UserInfoResponseBodyConverter();
    }
}
