package com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.ConverterFactory;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * 自定义RequestBodyConverter（相当于Retrofit自带的GsonRequestBodyConverter）
 */
public class FileRequestBodyConverter implements Converter<File, RequestBody> {
    @Override
    public RequestBody convert(File file) throws IOException {
        return RequestBody.create(MediaType.parse("application/otcet-stream"), file);
    }
}
