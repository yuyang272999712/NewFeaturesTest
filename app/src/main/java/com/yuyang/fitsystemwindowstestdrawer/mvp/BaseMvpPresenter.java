package com.yuyang.fitsystemwindowstestdrawer.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class BaseMvpPresenter<T> {
    /**
     * View 接口类型的弱引用
     */
    protected Reference<T> mViewRef;

    /**
     * 获取与之关联的View
     *
     * @return view
     */
    protected T getView() {
        return mViewRef.get();
    }

    /**
     * 建立关联：
     * View与Presenter建立关联
     *
     * @param view ViewInterface
     */
    public void attachView(T view) {
        mViewRef = new WeakReference<T>(view);
    }

    /**
     * 判断是否已经有View与Presenter建立了关联
     *
     * @return true 已经有View与Presenter建立管理，否则为false。
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 解除关联：
     * 销毁与之关联的View对象，以防内存泄露
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
