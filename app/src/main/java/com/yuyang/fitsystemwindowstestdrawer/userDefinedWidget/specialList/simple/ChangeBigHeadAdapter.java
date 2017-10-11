package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.specialList.simple;

import android.view.View;
import android.view.ViewGroup;

import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.specialList.ChangeBigHeadLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyang on 2017/10/11.
 */

public class ChangeBigHeadAdapter extends ChangeBigHeadLayout.Adapter {
    private List<DataInfoBean> list = new ArrayList<>();

    public void setData(){
        List<DataInfoBean> l1 = DataInfoBean.init();
        List<DataInfoBean> l2 = DataInfoBean.init();
        List<DataInfoBean> l3 = DataInfoBean.init();

        list.addAll(l1);
        list.addAll(l2);
        list.addAll(l3);

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, ViewGroup parent, int expendedHeight, int normalHeight) {
        ItemLayout itemView = new ItemLayout(parent.getContext());
        itemView.setData(position, list.get(position), expendedHeight, normalHeight);
        return itemView;
    }
}
