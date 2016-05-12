package com.yuyang.fitsystemwindowstestdrawer.recyclerViewSwipeRefresh.recyclerItemClickListener;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * recyclerView的item点击事件监听处理接口
 * 知识点：
 * 一、GestureDetectorCompat
 *  GestureDetectorCompat 就是处理手势的类：手势探测器，它比GestureDetector能更好兼容低版本的api，
 *  但使用方法是一致的
 *  实例化GestureDetectorCompat使需要传入一个手势监听器OnGestureListener，
 *  探测器识别出手势后就会回调手势监听器中对应的方法，我们就可以在回调方法中做我们想做的事情了。
 *  sdk为我们提供了两个手势监听器：OnGestureListener，OnDoubleTapListener。
    OnGestureListener的回调接口如下：
         //用户按下屏幕就会触发
         public boolean onDown(MotionEvent e);
         //如果是按下的时间超过瞬间，而且在按下的时候没有松开或者是拖动的，那么onShowPress就会执行
         public void onShowPress(MotionEvent e);
         //一次单独的轻击抬起操作,也就是轻击一下屏幕，就是普通点击事件
         public boolean onSingleTapUp(MotionEvent e);
         //在屏幕上拖动事件
         public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
         //长按触摸屏，超过一定时长，就会触发这个事件
         public void onLongPress(MotionEvent e);
         //滑屏，用户按下触摸屏、快速移动后松开
         public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
    OnDoubleTapListener的回调接口如下：
        //单击事件。用来判定该次点击是SingleTap而不是DoubleTap，
        //如果连续点击两次就是DoubleTap手势，如果只点击一次，
        //系统等待一段时间后没有收到第二次点击则判定该次点击为SingleTap而不是DoubleTap，
        //然后触发SingleTapConfirmed事件
        public boolean onSingleTapConfirmed(MotionEvent e);
        //双击事件
        public boolean onDoubleTap(MotionEvent e);
        //双击间隔中发生的动作。指触发onDoubleTap以后，在双击之间发生的其它动作
        public boolean onDoubleTapEvent(MotionEvent e);

    可以看出OnGestureListener主要回调各种单击事件，而OnDoubleTapListener回调各种双击事件。
    sdk 还提供了一个外部类SimpleOnGestureListener，这个类实现了上面两个接口的所有方法，但全都是空实现，
 */
public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private RecyclerView recyclerView;
    private GestureDetectorCompat gestureDetector;

    public OnRecyclerItemClickListener(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if(child != null){
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(child);
                onItemClick(viewHolder);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if(child != null){
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(child);
                onItemLongClick(viewHolder);
            }
        }
    }

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);
    public abstract void onItemLongClick(RecyclerView.ViewHolder viewHolder);
}
