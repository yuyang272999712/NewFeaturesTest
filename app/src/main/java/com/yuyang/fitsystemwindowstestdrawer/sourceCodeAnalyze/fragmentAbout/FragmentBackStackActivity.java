package com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.fragmentAbout;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;

/**
 * 1、管理Fragment回退栈(在配置发生变化后有点混乱)
 * 2、Fragment与Activity通信
 * 3、处理运行时配置发生变化
 * 4、Fragment与ActionBar和MenuItem集成
 * TODO yuyang 注意！！AppCompatActivity必须使用getSupportFragmentManager()才能正常使用Fragment回退栈，如果继承自Activity，那么使用getFragmentManager()能够正常使用回退栈
 */
public class FragmentBackStackActivity extends AppCompatActivity implements BackStackFragment1.FOneBtnClickListener, BackStackFragment2.FTwoBtnClickListener {
    private FragmentManager fragmentManager;

    private BackStackFragment1 mFOne;
    private BackStackFragment2 mFTwo;
    private BackStackFragment2 mFThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_back_stack);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("开发测试");
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        if(savedInstanceState == null) {//ZHU yuyang 防止屏幕旋转等状态变化导致的Fragment重复创建
            mFOne = BackStackFragment1.getInstance("第一个返回栈Fragment");
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_content_1, mFOne, "ONE");
            transaction.commit();
        }else {
            mFOne = (BackStackFragment1) fragmentManager.findFragmentByTag("ONE");
            mFTwo = (BackStackFragment2) fragmentManager.findFragmentByTag("TWO");
        }
        LogUtils.e("FragmentBackStackActivity","mFOne状态："+mFOne.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            ToastUtils.showShort(this, "点击了settings");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * BackStackFragment1按键的回调
     */
    @Override
    public void startNextFragment() {
        if (mFTwo == null) {
            mFTwo = BackStackFragment2.getInstance("第二个返回栈Fragment");
            //这是mFTwo的回调
            mFTwo.setfTwoBtnClickListener(this);
        }
        //TODO yuyang Fragment间的数据传递,这个方法，一般就是用于当前fragment由别的fragment启动，在完成操作后返回数据的
        mFTwo.setTargetFragment(mFOne, 100);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_content_1, mFTwo, "TWO");
        //ZHU yuyang 加入返回栈
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFTwoBtnClick() {
        if (mFThree == null){
            mFThree = BackStackFragment2.getInstance("第三个返回栈Fragment");
        }
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.hide(mFTwo);
        tx.add(R.id.fragment_content_1, mFThree, "THREE");
        tx.addToBackStack(null);
        tx.commit();
    }
}
