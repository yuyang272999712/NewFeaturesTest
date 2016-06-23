package com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.normalWay;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.LoadingDialogFragment;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 传统方式保存数据，使用onSaveInstanceState()方法
 * 不考虑加载时进行旋转的情况，有意的避开这种情况，后面例子会介绍解决方案
 */
public class SavedInstanceStateUsingActivity extends ListActivity {
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
    protected void onRestoreInstanceState(Bundle state) {
        LogUtils.i("onRestoreInstanceState");
        super.onRestoreInstanceState(state);
    }

    /**
     * 保存数据
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        LogUtils.i("onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("mDatas", mDatas);
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
        if (savedInstanceState != null){
            mDatas = savedInstanceState.getStringArrayList("mDatas");
        }
        if (mDatas == null){
            loadingDialog = new LoadingDialogFragment();
            loadingDialog.setCancelable(false);
            loadingDialog.show(getFragmentManager(), "laoding");

            loadingTask = new LoadingTask();
            loadingTask.execute();
        }else {
            initAdapter();
        }
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        setListAdapter(adapter);
    }

    private class LoadingTask extends AsyncTask<Void,Void,Void>{

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
