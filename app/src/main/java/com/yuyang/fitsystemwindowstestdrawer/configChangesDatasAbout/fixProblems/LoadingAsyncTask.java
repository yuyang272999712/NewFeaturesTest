package com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.fixProblems;

import android.os.AsyncTask;

import com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.LoadingDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yuyang on 16/6/23.
 */
public class LoadingAsyncTask extends AsyncTask<Void,Void,Void> {
    private FixProblemsActivity activity;
    /**
     * 是否完成
     */
    private boolean isCompleted;
    /**
     * 进度框
     */
    private LoadingDialogFragment mLoadingDialog;
    private List<String> items;

    public LoadingAsyncTask(FixProblemsActivity activity){
        this.activity = activity;
    }

    /**
     * 任务开始前现实加载框
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mLoadingDialog = new LoadingDialogFragment();
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.show(activity.getFragmentManager(),"load");
    }

    /**
     * 任务进行，这里模拟网络请求，线程停顿了2秒
     * @param params
     * @return
     */
    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        items = new ArrayList<String>(Arrays.asList("通过Fragment保存大量数据",
                "onSaveInstanceState保存数据",
                "getLastNonConfigurationInstance已经被弃用", "RabbitMQ", "Hadoop",
                "Spark"));
        return null;
    }

    /**
     * 后台任务完成，更新Activity
     * @param aVoid
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        isCompleted = true;
        notifyActivityTaskCompleted();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 设置Activity，因为Activity会一直变化
     *
     * @param activity
     */
    public void setActivity(FixProblemsActivity activity) {
        // 如果上一个Activity销毁，将与上一个Activity绑定的DialogFragment销毁
        if (activity == null) {
            mLoadingDialog.dismiss();
        }
        // 设置为当前的Activity
        this.activity = activity;
        // 开启一个与当前Activity绑定的加载框
        if (activity != null && !isCompleted) {
            mLoadingDialog = new LoadingDialogFragment();
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.show(activity.getFragmentManager(), "load");
        }
        // 如果完成，通知Activity
        if (isCompleted) {
            notifyActivityTaskCompleted();
        }
    }

    private void notifyActivityTaskCompleted() {
        if (null != activity) {
            activity.onTaskCompleted();
        }
    }

    public List<String> getItems() {
        return items;
    }

}
