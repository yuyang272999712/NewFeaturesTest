package com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.normalWay;

import android.app.ListActivity;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.LoadingDialogFragment;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 开发者自行处理配置变化
 * TODO yuyang 配置 android:configChanges="screenSize|orientation"
 *      Activity中重写onConfigurationChanged(Configuration newConfig)方法
 * 这样Activity在配置变化后就不会重启了
 */
public class UserHandleChangesActivity extends ListActivity {
    private ListAdapter adapter;
    private ArrayList<String> mDatas;
    private LoadingDialogFragment loadingDialog;
    private LoadingTask loadingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.i("onCreate");
        super.onCreate(savedInstanceState);
        initDatas(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtils.i("onConfigurationChanged");
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(this, "横屏", Toast.LENGTH_SHORT).show();
        }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "竖屏", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        LogUtils.i("onDestroy");
        super.onDestroy();
    }

    /**
     * 加载数据
     * @param savedInstanceState
     */
    private void initDatas(Bundle savedInstanceState) {
        loadingDialog = new LoadingDialogFragment();
        loadingDialog.setCancelable(false);
        loadingDialog.show(getFragmentManager(), "laoding");

        loadingTask = new LoadingTask();
        loadingTask.execute();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        setListAdapter(adapter);
    }

    private class LoadingTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mDatas = new ArrayList<String>(Arrays.asList("通过Fragment保存大量数据",
                    "onSaveInstanceState保存数据",
                    "getLastNonConfigurationInstance已经被弃用", "RabbitMQ", "Hadoop",
                    "Spark"));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            loadingDialog.dismiss();
            initAdapter();
        }
    }
}
