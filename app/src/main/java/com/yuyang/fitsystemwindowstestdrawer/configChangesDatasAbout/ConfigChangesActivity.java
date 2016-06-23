package com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.fixProblems.FixProblemsActivity;
import com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.normalWay.FragmentRetainDataActivity;
import com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.normalWay.SavedInstanceStateUsingActivity;
import com.yuyang.fitsystemwindowstestdrawer.configChangesDatasAbout.normalWay.UserHandleChangesActivity;

/**
 * Android 屏幕旋转 处理 AsyncTask 和 ProgressDialog 的最佳方案
 *
    1、概述
  众所周知，Activity在不明确指定屏幕方向和configChanges时，当用户旋转屏幕会重新启动。当然了，应对这种情况，Android给出了几种方案：
 a、如果是少量数据，可以通过onSaveInstanceState()和onRestoreInstanceState()进行保存与恢复。
  Android会在销毁你的Activity之前调用onSaveInstanceState()方法，于是，你可以在此方法中存储关于应用状态的数据。
  然后你可以在onCreate()或onRestoreInstanceState()方法中恢复。
 b、如果是大量数据，使用Fragment保持需要恢复的对象。
 c、自已处理配置变化。
  注:getLastNonConfigurationInstance()已经被弃用，被上述方法二替代。
    2、难点
  假设当前Activity在onCreate中启动一个异步线程去夹在数据，当然为了给用户一个很好的体验，会有一个ProgressDialog，
 当数据加载完成，ProgressDialog消失，设置数据。
 这里，如果在异步数据完成加载之后，旋转屏幕，使用上述a、b两种方法都不会很难，无非是保存数据和恢复数据。
 但是，如果正在线程加载的时候，进行旋转，会存在以下问题：
 a)此时数据没有完成加载，onCreate重新启动时，会再次启动线程；而上个线程可能还在运行，并且可能会更新已经不存在的控件，造成错误。
 b)关闭ProgressDialog的代码在线程的onPostExecutez中，但是上个线程如果已经杀死，无法关闭之前ProgressDialog。
 c)谷歌的官方不建议使用ProgressDialog，这里我们会使用官方推荐的DialogFragment来创建我的加载框，
  这样，其实给我们带来一个很大的问题，DialogFragment说白了是Fragment，和当前的Activity的生命周期会发生绑定，
  我们旋转屏幕会造成Activity的销毁，当然也会对DialogFragment造成影响。
 */
public class ConfigChangesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_changes);
    }

    /**
     * 不考虑屏幕旋转，使用传统方式保存恢复数据，如果在旋转前数据未加载成功，程序将报错
     * @param view
     */
    public void gotoAsyncTaskSaveInstanceState(View view){
        Intent intent = new Intent(this, SavedInstanceStateUsingActivity.class);
        startActivity(intent);
    }

    /**
     * 使用Fragment保存数据
     * @param view
     */
    public void gotoRetainedFragment(View view){
        Intent intent = new Intent(this, FragmentRetainDataActivity.class);
        startActivity(intent);
    }

    /**
     * 开发者自己处理配置变化
     * @param view
     */
    public void gotoUserHandleChanges(View view){
        Intent intent = new Intent(this, UserHandleChangesActivity.class);
        startActivity(intent);
    }

    /**
     *  Android 屏幕旋转 处理 AsyncTask 和 ProgressDialog 的最佳方案
     * @param view
     */
    public void gotoFixProblemsActivity(View view){
        Intent intent = new Intent(this, FixProblemsActivity.class);
        startActivity(intent);
    }
}
