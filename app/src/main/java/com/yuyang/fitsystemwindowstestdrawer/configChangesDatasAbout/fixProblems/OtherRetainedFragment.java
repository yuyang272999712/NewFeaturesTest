package com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.fixProblems;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * 数据持久化的Fragment
 */
public class OtherRetainedFragment extends Fragment {
    //在该Fragment中保持一个异步任务
    private AsyncTask asyncTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public AsyncTask getAsyncTask() {
        return asyncTask;
    }

    public void setAsyncTask(AsyncTask asyncTask) {
        this.asyncTask = asyncTask;
    }
}
