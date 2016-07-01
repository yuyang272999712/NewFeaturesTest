package com.yuyang.fitsystemwindowstestdrawer.recyclerView.removeItem;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.DensityUtils;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

/**
 * 滑动删除item的RecyclerView
 */
public class RecyclerViewSlidingItem extends RecyclerView {
    //手指按下时选择的Item
    private int pos;
    //每个Item的布局内容
    private TextView textView;
    private ImageView imageView;
    private LinearLayout itemLayout;
    //手指按下时的坐标
    private int xDown;
    private int yDown;
    //最大滑动距离（再滑item就出去了）
    private int maxLength;
    //开始x坐标
    private int mStartX = 0;
    //系统配置的判读滑动的最小距离
    private int mTouchSlop;
    //当滑动超过一半或不够一半时抬起手指itemView的继续滑动
    private Scroller mScroller;
    //图标只进行一次动画
    private boolean isFirst;
    //是否正在侧滑
    private boolean isSliding;

    public RecyclerViewSlidingItem(Context context) {
        this(context, null);
    }
    public RecyclerViewSlidingItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public RecyclerViewSlidingItem(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        maxLength = DensityUtils.dp2px(context, 180);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        int action = e.getAction();
        switch (action){
            //TODO yuyang 在ListView当中，有一个pointToPosition(x, y)方法可以根据坐标获取到当前的position，在RecyclerView中没有这个方法，需要我们自己动手写一个。
            case MotionEvent.ACTION_DOWN:
                xDown = x;
                yDown = y;
                //通过点击的坐标计算当前的position
                int firstPosition = ((LinearLayoutManager)getLayoutManager()).findFirstVisibleItemPosition();
                Rect frame = new Rect();
                //使用getChildCount()与getChildAt()进行取值，只能是当前可见区域的子项！取值范围在0到getLastVisiblePosition()减去getFirstVisiblePosition()之间
                int count = getChildCount();
                for (int i=count-1;i>=0;i--){
                    View child = getChildAt(i);
                    if (child.getVisibility() == VISIBLE){
                        child.getHitRect(frame);//获取相对于父控价的区域
                        if (frame.contains(x,y)){
                            pos = firstPosition + i;
                        }
                    }
                }
                //通过position得到item的viewHolder
                View child = getChildAt(pos - firstPosition);
                MyViewHolder holder = (MyViewHolder) getChildViewHolder(child);
                itemLayout = holder.layout;
                textView = (TextView) itemLayout.findViewById(R.id.item_delete_txt);
                imageView = (ImageView) itemLayout.findViewById(R.id.item_delete_img);
                mStartX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - xDown;
                int dy = y - yDown;
                LogUtils.e("MOVE事件，x坐标："+x+"; 原始Down_x坐标："+xDown);
                //判断是否是水平滑动
                if (Math.abs(dy) < mTouchSlop * 2 && Math.abs(dx) > mTouchSlop || isSliding) {
                    //各种情况下停止水平滑动
                    int scrollX = itemLayout.getScrollX();
                    int newScrollX = mStartX - x;
                    if (newScrollX < 0 && scrollX <= 0) {
                        newScrollX = 0;
                    } else if (newScrollX > 0 && scrollX >= maxLength) {
                        newScrollX = 0;
                    }
                    if (scrollX > maxLength / 2) {
                        textView.setVisibility(GONE);
                        imageView.setVisibility(VISIBLE);
                        if (isFirst) {
                            ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 1.2f, 1f);
                            ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 1.2f, 1f);
                            AnimatorSet animSet = new AnimatorSet();
                            animSet.play(animatorX).with(animatorY);
                            animSet.setDuration(800);
                            animSet.start();
                            isFirst = false;
                        }
                    } else {
                        textView.setVisibility(VISIBLE);
                        imageView.setVisibility(GONE);
                    }
                    itemLayout.scrollBy(newScrollX, 0);
                    //使竖直方向的滑动为0
                    isSliding = true;
                    mStartX = x;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = itemLayout.getScrollX();
                if (scrollX > maxLength / 2) {
                    ((RecyclerAdapter) getAdapter()).removeRecycle(pos);
                } else {
                    mScroller.startScroll(scrollX, 0, -scrollX, 0);
                    invalidate();
                }
                isFirst = true;
                isSliding = false;
                break;
        }
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            itemLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
