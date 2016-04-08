package com.yuyang.fitsystemwindowstestdrawer.horizontalFling;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * Created by yuyang on 16/2/3.
 * 测试水平滑动控件
 */
public class HorizontalFlingActivity extends AppCompatActivity {
    private View leftView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.horizontal_fling_activity);

        getWindowManager();
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float width = metrics.widthPixels;

        leftView = findViewById(R.id.leftView);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HorizontalFlingActivity.this, "开发测试", Toast.LENGTH_LONG).show();
            }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HorizontalFlingActivity.this, "开发测试button", Toast.LENGTH_LONG).show();
            }
        });

        MyTask task = new MyTask(this);
        task.execute();
    }

    private class MyTask extends AsyncTask<Void, Integer, Boolean>{
        private Context mContext = null;
        private ProgressDialog mDialog = null;
        private int mConut = 0;

        public MyTask(Context context){
            this.mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(mContext);
            mDialog.setMax(100);
            mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mDialog.setProgress(values[0]);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            while (mConut < 100){
                publishProgress(mConut);
                mConut += 20;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean == true) {
                mDialog.dismiss();
            }else {
                Toast.makeText(mContext, "出错了", Toast.LENGTH_LONG).show();
            }
        }
    }
}
