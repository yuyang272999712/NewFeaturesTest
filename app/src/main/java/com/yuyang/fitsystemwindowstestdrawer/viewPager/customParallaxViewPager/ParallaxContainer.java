package com.yuyang.fitsystemwindowstestdrawer.viewPager.customParallaxViewPager;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 视差效果的ViewPager父容器（其中包含ViewPager、每个页面的总ViewGroup）
 */

public class ParallaxContainer extends FrameLayout {
    private String TAG = "ParallaxContainer";
    private List<View> parallaxViews = new ArrayList<>();//保存所有参与动画的View
    private ViewPager viewPager;
    private ParallaxPagerAdapter adapter;
    private int pageCount;//viewPager中的页数
    private boolean isLooping = false;//viewPager是否循环滑动
    private int containerWidth;//容器宽度
    private int currentPosition = 0;//当前所处的pager位置
    private Context context;
    private ViewPager.OnPageChangeListener pageChangeListener;//ViewPager的滑动监听

    private ImageView manImageView;//站在屏幕中央的小人
    private static final long DELAY_TIME = 600;//小人动画在滑动结束后持续时间
    private boolean isEnd = false;//小人的动画是否结束

    public ParallaxContainer(Context context) {
        super(context);
        this.context = context;
        adapter = new ParallaxPagerAdapter(context);
    }

    public ParallaxContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        adapter = new ParallaxPagerAdapter(context);
    }

    public ParallaxContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        adapter = new ParallaxPagerAdapter(context);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        containerWidth = getMeasuredWidth();
        if (viewPager != null) {
            pageChangeListener.onPageScrolled(viewPager.getCurrentItem(), 0, 0);
        }
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * 设置滑动内容
     * @param inflater
     * @param childIds
     */
    public void setChildrens(LayoutInflater inflater, int... childIds){
        if (getChildCount() > 0){
            removeAllViews();
            Log.w(TAG,"第二次调用setChildrens()方法，之前的所有ChildView都会被清空");
        }
        //!--yuyang 重新设置LayoutInflater,使用自定的Factory将View中的自定义属性添加至View的Tag中
        //ZHU yuyang 小心V7包中AppCompatActivity类对Factory的重写，可能导致这里的Factory不起作用
        ParallaxLayoutInflater parallaxLayoutInflater = new ParallaxLayoutInflater(inflater, getContext());
        for (int childId:childIds){//将所有子View添加进布局
            parallaxLayoutInflater.inflate(childId, this);
        }
        pageCount = getChildCount();//获取ViewPager的页数＝当前的子View数量
        for (int i = 0; i < pageCount; i++) {//获取childView中的所有最底层的需要动画的View
            View view = getChildAt(i);
            addParallaxView(view, i);
        }

        updateAdapterCount();

        viewPager = new ViewPager(getContext());
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        attachOnPageChangeListener();//给ViewPager添加监听事件
        viewPager.setAdapter(adapter);
        addView(viewPager, 0);//将ViewPager添加到容器中
    }

    /**
     * 递归方法，找到所有ViewGroup中的View，并保存至parallaxViews集合
     * @param view
     * @param pageIndex
     */
    private void addParallaxView(View view, int pageIndex) {
        if (view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i=0,childCount=viewGroup.getChildCount(); i<childCount; i++){
                addParallaxView(viewGroup.getChildAt(i), pageIndex);
            }
        }

        ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.id_parallax_view_tag);
        if (tag != null){
            tag.index = pageIndex;
            parallaxViews.add(view);
        }
    }

    /**
     * 给ViewPager添加监听事件
     */
    private void attachOnPageChangeListener() {
        pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //ZHU yuyang 自定义的动画，当page到第四页是将manImageView移除／移入页面
                if (position == 3 && manImageView != null) {
                    manImageView.setX(manImageView.getLeft() - positionOffsetPixels);
                }
                ParallaxViewTag tag;
                for (View view : parallaxViews) {//遍历所有最底层的元素
                    tag = (ParallaxViewTag) view.getTag(R.id.id_parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }

                    if ((position == tag.index - 1 || (isLooping && (position == tag.index - 1 + pageCount)))
                            && containerWidth != 0) {
                        // make visible
                        view.setVisibility(VISIBLE);
                        // slide in from right
                        view.setTranslationX((containerWidth - positionOffsetPixels) * tag.xIn);
                        // slide in from top
                        view.setTranslationY(0 - (containerWidth - positionOffsetPixels) * tag.yIn);
                        // fade in
                        view.setAlpha(1.0f - (containerWidth - positionOffsetPixels) * tag.alphaIn / containerWidth);
                    } else if (position == tag.index) {
                        // make visible
                        view.setVisibility(VISIBLE);
                        // slide out to left
                        view.setTranslationX(0 - positionOffsetPixels * tag.xOut);
                        // slide out to top
                        view.setTranslationY(0 - positionOffsetPixels * tag.yOut);
                        // fade out
                        view.setAlpha(1.0f - positionOffsetPixels * tag.alphaOut / containerWidth);
                    } else {
                        view.setVisibility(GONE);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (manImageView != null){
                    AnimationDrawable animationDrawable = (AnimationDrawable) manImageView.getBackground();
                    if (state == ViewPager.SCROLL_STATE_DRAGGING){//正在滑动
                        isEnd = false;
                        animationDrawable.start();
                    }else {
                        finishAnim(animationDrawable);
                    }
                }
            }
        };
        viewPager.addOnPageChangeListener(pageChangeListener);
    }

    /**
     * 结束动画
     * @param animationDrawable
     */
    private void finishAnim(final AnimationDrawable animationDrawable) {
        if (isEnd){
            return;
        }
        isEnd = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(DELAY_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (animationDrawable.isRunning() && isEnd){
                    animationDrawable.stop();
                }
            }
        }).start();
    }

    /**
     * 刷新adapter大小
     */
    private void updateAdapterCount() {
        adapter.setCount(isLooping ? Integer.MAX_VALUE : pageCount);
    }

    public void setLooping(boolean looping){
        this.isLooping = looping;
        updateAdapterCount();
    }

    public void setManImageView(ImageView imageView){
        this.manImageView = imageView;
    }
}
