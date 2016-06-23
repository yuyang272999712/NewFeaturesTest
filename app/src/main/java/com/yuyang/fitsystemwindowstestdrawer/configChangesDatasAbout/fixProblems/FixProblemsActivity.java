package com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.fixProblems;

import android.app.FragmentManager;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

import java.util.ArrayList;

/**
 * 异步任务在ConfigChanges时，Activity重启继续运行的解决办法
 * 1、使用Fragment持久化数据
 * 2、Fragment中持久化的就是异步任务
 * 3、Activity因配置变化重启时，关闭异步任务中的加载框，重启后再显示新的加载框
 * 这样在加载数据时旋转屏幕，不会对加载任务进行中断，且对用户而言，等待框在加载完成之前都正常显示
 */
public class FixProblemsActivity extends ListActivity {
    private ListAdapter adapter;
    private ArrayList<String> mDatas;
    private LoadingAsyncTask loadingTask;
    //保存数据的Fragment
    private OtherRetainedFragment dataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.i("onCreate");
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getFragmentManager();
        //获取持久化数据保存的Fragment
        dataFragment = (OtherRetainedFragment) fragmentManager.findFragmentByTag("loadFragment");
        if (dataFragment == null){
            dataFragment = new OtherRetainedFragment();
            fragmentManager.beginTransaction().add(dataFragment, "loadFragment").commit();
        }
        //从Fragment中获取加载数据的异步线程
        loadingTask = (LoadingAsyncTask) dataFragment.getAsyncTask();
        if (loadingTask == null){//如果线程为空，则创建线程，并持久化。
            loadingTask = new LoadingAsyncTask(this);
            dataFragment.setAsyncTask(loadingTask);
            loadingTask.execute();
        }else {
            loadingTask.setActivity(this);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        LogUtils.i("onRestoreInstanceState");
    }

    /**
     * 保存数据
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //TODO yuyang super.onSaveInstanceState(outState) 方法之后不允许再更新UI！！！所以这里不许在之前执行
        loadingTask.setActivity(null);//loadingTask内部会关闭加载框
        super.onSaveInstanceState(outState);
        LogUtils.i("onSaveInstanceState");
    }

    @Override
    protected void onDestroy() {
        LogUtils.i("onDestroy");
        super.onDestroy();
    }

    /**
     * 异步任务回调
     */
    public void onTaskCompleted() {
        mDatas = (ArrayList<String>) loadingTask.getItems();
        adapter = new ArrayAdapter<String>(FixProblemsActivity.this,
                android.R.layout.simple_list_item_1, mDatas);
        setListAdapter(adapter);
    }
}
