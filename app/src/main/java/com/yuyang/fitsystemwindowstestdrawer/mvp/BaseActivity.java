package com.yuyang.fitsystemwindowstestdrawer.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 基类
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {
    /**
     * Presenter 对象
     */
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建Presenter对象
        mPresenter = createPresenter();
        // View 与 Presenter建立关联
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Activity 销毁的时候与Presenter解除关联，也就是销毁当前Activity在Presenter中的引用。
        mPresenter.detachView();
    }

    protected abstract T createPresenter();
}
