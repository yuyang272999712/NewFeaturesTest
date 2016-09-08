package com.yuyang.fitsystemwindowstestdrawer.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;


/**
 * <ul>
 *  <li>Activity基类，所有Activity继承此Fragment进行统一管理 </li>
 *  <li>泛型V ：为当前Fragment实现的ViewInterface类</li>
 *  <li>泛型T ：为当前View与之关联的Presenter类</li>
 *  <li>与{@link BaseMvpPresenter}建立抽象关联</li>
 *  <li>UI统一化</li>
 * </ul>
 */
public abstract class BaseMvpFragment<V , T extends BaseMvpPresenter<V>> extends Fragment{
    /**
     * Presenter 对象
     */
    protected T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建Presenter对象
        mPresenter = createPresenter();
        // View 与 Presenter建立关联
        mPresenter.attachView((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Fragment 销毁的时候与Presenter接触关联，也就是销毁当前Fragment在Presenter中的引用。
        mPresenter.detachView();
    }

    /**
     * 创建Presenter对象
     * @return 返回一个继承自 {@link BaseMvpPresenter} 的Presenter对象
     */
    protected abstract T createPresenter();

}
