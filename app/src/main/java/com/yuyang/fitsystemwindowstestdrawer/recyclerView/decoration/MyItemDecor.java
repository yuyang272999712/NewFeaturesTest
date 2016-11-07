package com.yuyang.fitsystemwindowstestdrawer.recyclerView.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 自定义ItemDecoration
 */

public class MyItemDecor extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            boolean isFirstRow = false;
            boolean isFirstColumn = false;
            if (position < spanCount){//第一行的item
                isFirstRow = true;
            }
            if(position%spanCount == 0){//第一列的item
                isFirstColumn = true;
            }
            if (isFirstRow & isFirstColumn){
                outRect.set(50, 50, 50, 50);
            }else if (isFirstRow){
                outRect.set(0, 50, 50, 50);
            }else if (isFirstColumn){
                outRect.set(50, 0, 50, 50);
            }else {
                outRect.set(0, 0, 50, 50);
            }
        }else if (layoutManager instanceof LinearLayoutManager){
            if (position == 0){
                outRect.set(50, 50, 50, 50);
            }else {
                outRect.set(50, 0, 50, 50);
            }
        }
    }

    /**
     * RecyclerView绘制时，这个方法最先执行，所有它所绘制的内容会在Item之下
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child))+50/2-10/2;
            final int bottom = top + 10;
            c.drawRect(left, top, right, bottom, paint);
        }
    }

    /**
     * RecyclerView绘制时，这个方法最后执行，所有它所绘制的内容会在所有内容之上，即总是可见的
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int width = parent.getWidth();
        int height = parent.getChildAt(0).getHeight();
        Paint paint = new Paint();
        paint.setShader(new LinearGradient(0, 0, 0, height, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP));
        c.drawRect(0, 0, width, height, paint);
        paint.setShader(null);
        paint.setColor(Color.parseColor("#8AFF0004"));
        c.drawCircle(width/2, height*2, height/2, paint);
    }
}
