package com.yuyang.fitsystemwindowstestdrawer.homeDemo.complexHome;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yuyang on 2017/3/7.
 * LinearLayoutManager 左右分隔
 */

public class LinearHorizontalItemDecor extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager){
            boolean isFirst = false;//是否是第一个
            boolean isEnd = false;//是否是最后一个
            if (position == 0){
                isFirst = true;
            }
            if (position == parent.getAdapter().getItemCount()-1){
                isEnd = true;
            }
            if (isFirst){
                outRect.set(16, 0, 5, 0);
            }else if (isEnd){
                outRect.set(0, 0, 16, 0);
            }else {
                outRect.set(0, 0, 5, 0);
            }
        }
    }
}
