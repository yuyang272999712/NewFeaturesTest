package com.yuyang.fitsystemwindowstestdrawer.internetAbout.okHttpAbout;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * OkHttp使用
 */
public class OkHttpAboutActivity extends AppCompatActivity {
    private static Handler handler = new Handler();

    private Toolbar toolbar;
    private TextView textView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_about);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("OkHttp中的各种请求");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textView = (TextView) findViewById(R.id.textView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    /**
     * post请求
     * @param view
     */
    public void postRequest(View view){
        Map<String, String> params = new HashMap<>();
        params.put("wd","测试");
        OkHttpUtils.postParams("http://www.baidu.com/s", params, new CallbackBase<String>() {
            @Override
            public String parseNetworkResponse(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(OkHttpAboutActivity.this, "请求失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                textView.setText(response);
            }
        });
    }

    /**
     * get请求
     * @param view
     */
    public void getRequest(View view){
        Map<String, String> params = new HashMap<>();
        params.put("wd","测试");
        OkHttpUtils.getParams("http://www.baidu.com/s", params, new CallbackBase<String>() {
            @Override
            public String parseNetworkResponse(Response response) throws Exception {
                Log.e("OkHttp缓存测试:cache",response.cacheResponse()+"");
                Log.e("OkHttp缓存测试:network",response.networkResponse()+"");
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(OkHttpAboutActivity.this, "请求失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                textView.setText(response);
            }
        });
    }

    /**
     * 上传文件
     * @param view
     */
    public void uploadFile(View view){
        Map<String, String> params = new HashMap<>();
        params.put("user","yuyang");
        params.put("password", "password");
        File file = new File(Environment.getExternalStorageDirectory(), "sky_1.jpg");
        if (!file.exists()){
            Toast.makeText(this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "https://api.imgur.com/3/image";
        OkHttpUtils.uploadFile(url, params, file, new CallbackBase<String>(){

            @Override
            public String parseNetworkResponse(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(OkHttpAboutActivity.this, "上传失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Toast.makeText(OkHttpAboutActivity.this, "上传成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void inProgress(float progress, long total) {
                super.inProgress(progress, total);
                mProgressBar.setProgress((int) (100 * progress));
            }
        });
    }

    /**
     * 下载文件
     * @param view
     */
    public void downFile(View view){
        final String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        final String destFileName = "OkHttp_"+ new Date()+".jar";
        String url = "https://codeload.github.com/hongyangAndroid/okhttputils/zip/master";
        OkHttpUtils.downFile(url, new CallbackBase<File>(){

            @Override
            public File parseNetworkResponse(Response response) throws Exception {
                //保存文件
                return saveFile(response);
            }

            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(OkHttpAboutActivity.this, "下载失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(File response) {
                Toast.makeText(OkHttpAboutActivity.this, "文件下载成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void inProgress(float progress, long total) {
                super.inProgress(progress, total);
                mProgressBar.setProgress((int) (100 * progress));
            }

            public File saveFile(Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    final long total = response.body().contentLength();

                    long sum = 0;

                    File dir = new File(destFileDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File file = new File(dir, destFileName);
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        sum += len;
                        fos.write(buf, 0, len);
                        final long finalSum = sum;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                inProgress(finalSum * 1.0f / total, total);
                            }
                        });
                    }
                    fos.flush();

                    return file;

                } finally {
                    try {
                        response.body().close();
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }

                }
            }
        });
    }

}
