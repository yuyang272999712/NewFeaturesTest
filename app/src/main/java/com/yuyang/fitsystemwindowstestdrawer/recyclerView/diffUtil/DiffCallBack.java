package com.yuyang.fitsystemwindowstestdrawer.recyclerView.diffUtil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * TODO yuyang DiffUtil核心类 用来判断 新旧Item是否相等
 *  getChangePayload()方法可按需重写
 */
public class DiffCallBack extends DiffUtil.Callback {
    private List<DiffutilItem> mOldDatas, mNewDatas;

    public DiffCallBack(List<DiffutilItem> mOldDatas, List<DiffutilItem> mNewDatas) {
        this.mOldDatas = mOldDatas;
        this.mNewDatas = mNewDatas;
    }

    //老数据集size
    @Override
    public int getOldListSize() {
        return mOldDatas != null ? mOldDatas.size():0;
    }

    //新数据集size
    @Override
    public int getNewListSize() {
        return mNewDatas != null ? mNewDatas.size():0;
    }

    /**
     * 被DiffUtil调用，用来判断 两个对象是否是相同的Item。
     * 例如，如果你的Item有唯一的id字段，这个方法就 判断id是否相等。
     * 本例判断title字段是否一致
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list
     * @return True if the two items represent the same object or false if they are different.
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldDatas.get(oldItemPosition).getTitle().equals(mNewDatas.get(newItemPosition).getTitle());
    }

    /**
     * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
     * DiffUtil用返回的信息（true false）来检测当前item的内容是否发生了变化
     * DiffUtil 用这个方法替代equals方法去检查是否相等。
     * 所以你可以根据你的UI去改变它的返回值
     * 例如，如果你用RecyclerView.Adapter 配合DiffUtil使用，你需要返回Item的视觉表现是否相同。
     * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list which replaces the oldItem
     * @return True if the contents of the items are the same or false if they are different.
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        DiffutilItem oldItem = mOldDatas.get(oldItemPosition);
        DiffutilItem newItem = mNewDatas.get(newItemPosition);
        if (oldItem.getImgId() != newItem.getImgId()){
            return false;
        }
        if (!oldItem.getDes().equals(newItem.getDes())){
            return false;
        }
        return true;//两个data内容是相同的
    }

    /**
        高级用法只涉及到两个方法，
     我们需要分别实现DiffUtil.Callback的
     public Object getChangePayload(int oldItemPosition, int newItemPosition)方法，
     返回的Object就是表示Item改变了哪些内容。

     再配合RecyclerView.Adapter的
     public void onBindViewHolder(VH holder, int position, List<Object> payloads)方法，
     完成定向刷新。这里的第三个参数payloads就是getChangePayload方法返回的Object

     方法解释：
            当{@link #areItemsTheSame(int, int)} 返回true，且{@link #areContentsTheSame(int, int)} 返回false时，
        DiffUtils会回调此方法（说明该item中有部分字段发生了变化，例如：图片url变化了），去得到这个Item（有哪些例如：图片url变化了）
        改变的payload。

     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        DiffutilItem oldItem = mOldDatas.get(oldItemPosition);
        DiffutilItem newItem = mNewDatas.get(newItemPosition);
        Bundle bundle = new Bundle();
        if (oldItem.getImgId() != newItem.getImgId()){
            bundle.putInt("IMG_ID", newItem.getImgId());
        }
        if (!oldItem.getDes().equals(newItem.getDes())){
            bundle.putString("DES", newItem.getDes());
        }
        return bundle;
    }
}
