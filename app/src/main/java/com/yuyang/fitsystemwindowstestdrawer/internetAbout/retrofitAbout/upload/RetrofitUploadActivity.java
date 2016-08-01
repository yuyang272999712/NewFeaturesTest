package com.yuyang.fitsystemwindowstestdrawer.internetAbout.retrofitAbout.upload;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 文件上传
 */
public class RetrofitUploadActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://api.github.com/";

    private Button requestData;
    private TextView userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        findViews();
        setAction();
    }

    private void setAction() {
        requestData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = "";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .build();
                FileUploadService uploadService = retrofit.create(FileUploadService.class);

                //构建要上传的文件
                File file = new File(filename);
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("application/otcet-stream"), file);

                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);

                String descriptionString = "This is a description";
                RequestBody description =
                        RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

                Call<ResponseBody> call = uploadService.upload(description, body);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.v("Upload", "success");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Upload error:", t.getMessage());
                    }
                });
            }
        });
    }

    private void findViews() {
        requestData = (Button) findViewById(R.id.retrofit_request_data);
        userInfo = (TextView) findViewById(R.id.user_info);
    }
}
