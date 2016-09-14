package com.yuyang.fitsystemwindowstestdrawer.internetAbout.downFileService;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yuyang on 16/9/14.
 */
public class DownLoadFileUtil {
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    private static final String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    //private static final String destFileName = "Tomcat_"+ new Date()+".zip";

    public static Call loadFile(final FileInfo fileInfo, final CallBack callBack){
        String url = fileInfo.getFileUrl();
        Request request = new Request.Builder().url(url).tag(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                callBack.onError(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!call.isCanceled()) {
                    try {
                        final File file = saveFile(response, fileInfo.getFileName(), callBack);
                        callBack.onSuccess(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                        onFailure(call, (IOException) e);
                    }
                }
            }
        });
        return call;
    }

    private static File saveFile(Response response, String fileName, CallBack callBack) throws Exception {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            //TODO yuyang OkHttp获取下载文件的总大小
            final long total = response.body().contentLength();

            if (total <= 0){
                throw new IOException("下载的文件不存在");
            }

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                long finalSum = sum;
                callBack.inProgress(finalSum * 1.0f / total, total);
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

    public interface CallBack{
        void onError(Call call, IOException e);
        void onSuccess(File file);
        void inProgress(float current,long total);
    }
}
