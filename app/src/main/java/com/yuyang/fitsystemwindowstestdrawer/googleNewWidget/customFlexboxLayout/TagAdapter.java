package com.yuyang.fitsystemwindowstestdrawer.googleNewWidget.customFlexboxLayout;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yuyang on 16/5/18.
 */
public abstract class TagAdapter<T> {
    private List<T> mDatas;
    private OnAdapterDataChanged mOnAdapterDataChanged;//数据发生变化时做相应处理
    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();//设置默认选中项

    public TagAdapter(List<T> datas){
        if(datas == null){
            datas = new ArrayList<>();
        }
        mDatas = datas;
    }

    public TagAdapter(T[] datas) {
        mDatas = new ArrayList<>(Arrays.asList(datas));
    }

    /**
     * 生成ItemView
     * @param parent
     * @param position
     * @param t
     * @return
     */
    protected abstract View getView(ViewGroup parent, int position, T t);

    protected int getCount(){
        return mDatas.size();
    }

    protected T getItem(int position){
        return mDatas.get(position);
    }

    public void notifyDataSetChanged(){
        if (mOnAdapterDataChanged != null){
            mOnAdapterDataChanged.onChange();
        }
    }

    public void setSelectedList(int... poses) {
        Set<Integer> set = new HashSet<>();
        for (int pos : poses)
        {
            set.add(pos);
        }
        setSelectedList(set);
    }

    public void setSelectedList(Set<Integer> set) {
        mCheckedPosList.clear();
        if (set != null)
            mCheckedPosList.addAll(set);
        notifyDataSetChanged();
    }

    public HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }

    /**
     * 传入position，如果返回true则认为默认选中
     *  主要用于处理默认选中
     * @param position
     * @return
     */
    protected boolean select(int position){
        return false;
    }
    public boolean setSelected(int position, T t)
    {
        return false;
    }

    public void setmOnAdapterDataChanged(OnAdapterDataChanged mOnAdapterDataChanged) {
        this.mOnAdapterDataChanged = mOnAdapterDataChanged;
    }

    public void setmDatas(@NonNull List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    interface OnAdapterDataChanged {
        void onChange();
    }
}
