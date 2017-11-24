package com.yuyang.fitsystemwindowstestdrawer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * ZHU yuyang MultiDex初次启动APP优化
 * 该Activity属于另外一个进程（属于：mini进程），可以看Manifest文件重的配置,负责加载dex
 */

public class LoadResActivity extends Activity {
    private Messenger messenger;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
        setContentView(R.layout.activity_load_res);

        Log.e("LoadResActivity", "start install");
        Intent from = getIntent();
        messenger = from.getParcelableExtra("MESSENGER");

        LoadDexTask dexTask = new LoadDexTask();
        dexTask.execute();
    }

    class LoadDexTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                MultiDex.install(getApplication());
                Log.e("LoadResActivity", "finish install");
                messenger.send(new Message());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void o) {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onBackPressed() {
        //无法退出
    }
}
