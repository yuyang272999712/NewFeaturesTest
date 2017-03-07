package com.yuyang.fitsystemwindowstestdrawer.homeDemo.complexHome;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yuyang on 2017/3/7.
 * GridLayoutManager 右下分隔
 */

public class RightBottomItemDecor extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            int spanIndex = layoutParams.getSpanIndex();
            int spanSize = layoutParams.getSpanSize();
            boolean isLeftMore = false;//是否是最左侧的
            boolean isRightMore = false;//是否是最右侧的
            if (spanIndex == 0){
                isLeftMore = true;
            }
            if (spanIndex+spanSize == spanCount){
                isRightMore = true;
            }
            if (isLeftMore && isRightMore){//说明这行只有这一个元素
                outRect.set(0, 0, 0, 5);
            }else if (isLeftMore){
                outRect.set(16, 0, 5, 5);
            }else if (isRightMore){
                outRect.set(0, 0, 16, 5);
            }else {
                outRect.set(0, 0, 5, 5);
            }
        }
    }
}
