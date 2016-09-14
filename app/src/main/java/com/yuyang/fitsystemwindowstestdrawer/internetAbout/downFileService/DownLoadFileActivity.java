package com.yuyang.fitsystemwindowstestdrawer.internetAbout.downFileService;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/9/14.
 */
public class DownLoadFileActivity extends AppCompatActivity {
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private Toolbar toolbar;

    private AbstractDownLoadServiceReceiver downLoadServiceReceiver = new AbstractDownLoadServiceReceiver(){

        @Override
        public void onProgress(int fileId, float progress) {
            switch (fileId){
                case 1:
                    progressBar1.setProgress((int) (progress*100));
                    break;
                case 2:
                    progressBar2.setProgress((int) (progress*100));
                    break;
                case 3:
                    progressBar3.setProgress((int) (progress*100));
                    break;
            }
        }

        @Override
        public void onError(int fileId, Exception exception) {
            Toast.makeText(DownLoadFileActivity.this, "文件"+fileId+"下载失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCompleted(int fileId) {
            Toast.makeText(DownLoadFileActivity.this, "文件"+fileId+"下载完成", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("并发下载");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressBar1 = (ProgressBar) findViewById(R.id.progress_1);
        progressBar2 = (ProgressBar) findViewById(R.id.progress_2);
        progressBar3 = (ProgressBar) findViewById(R.id.progress_3);

        downLoadServiceReceiver.register(this);
    }

    public void beginDown(View view){
        switch (view.getId()){
            case R.id.begin1:
                FileInfo fileInfo1 = new FileInfo(1, "第一个文件", "http://mirrors.cnnic.cn/apache/tomcat/tomcat-9/v9.0.0.M10/bin/apache-tomcat-9.0.0.M10.zip", 0);
                DownLoadFileService.startDownLoadFile(DownLoadFileActivity.this, fileInfo1);
                break;
            case R.id.begin2:
                FileInfo fileInfo2 = new FileInfo(2, "第二个文件", "http://mirrors.cnnic.cn/apache/tomcat/tomcat-9/v9.0.0.M10/bin/apache-tomcat-9.0.0.M10.zip", 0);
                DownLoadFileService.startDownLoadFile(DownLoadFileActivity.this, fileInfo2);
                break;
            case R.id.begin3:
                FileInfo fileInfo3 = new FileInfo(3, "第三个文件", "http://mirrors.cnnic.cn/apache/tomcat/tomcat-9/v9.0.0.M10/bin/apache-tomcat-9.0.0.M10.zip", 0);
                DownLoadFileService.startDownLoadFile(DownLoadFileActivity.this, fileInfo3);
                break;
        }
    }

    public void cancelDown(View view){
        switch (view.getId()){
            case R.id.cancel1:
                DownLoadFileService.stopOneTask(this, 1);
                break;
            case R.id.cancel2:
                DownLoadFileService.stopOneTask(this, 2);
                break;
            case R.id.cancel3:
                DownLoadFileService.stopOneTask(this, 3);
                break;
        }
    }
}
