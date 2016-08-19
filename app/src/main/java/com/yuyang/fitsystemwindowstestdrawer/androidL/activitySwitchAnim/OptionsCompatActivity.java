package com.yuyang.fitsystemwindowstestdrawer.androidL.activitySwitchAnim;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.androidL.activitySwitchAnim.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 一、Theme中设置切换动画（例如：styles.xml中 TransitionTheme）
 *   这种方式太操蛋了！！！
 *
 * 二、overridePendingTransition()设置切换动画
 *
 * 三、ActivityOptionsCompat（也可使用ActivityOptions，但需要考虑兼容性）
 *  通过这个类来启动activity和添加动画
 *    常用方法：
 *      1、makeCustomAnimation(context, enterResId, exitResId)
 *          定制activity消失的动画 和 新activity出现的动画
 *      2、makeScaleUpAnimation(source, startX, startY, startWidth, startHeight)
 *          可以实现新的Activity从某个固定的坐标以某个大小扩大至全屏
 *          source：The View that the new activity is animating from
 *          startX、startY：拉伸开始的坐标
 *          startWidth、startHeight：拉伸开始的区域，（0，0 表示从无至全屏）
 *      3、makeSceneTransitionAnimation(activity, sharedElement, sharedElementName)
 *          当你需要当前界面中的某个元素和新界面中的元素有关时，你可以使用这个动画。
 *          必须给两个不同Activity的中的布局元素设定同样的一个android:transitionName，
 *          然后还需要一个标志来告诉Window执行动画，这个效果只在5.x上有效
 *      4、makeSceneTransitionAnimation(Activity arg0, Pair<View, String>...  arg1)
 *          这个方法用于多个元素和新的Activity相关的情况，注意下第二个参数Pair这个键值对后面有...，标明是可以传入多个Pair对象的。
 *      5、makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY)
 *          将一个小块的Bitmap进行拉伸的动画
 *
 * 四、ViewAnimationUtils.createCircularReveal()
 *  圆形揭示效果
 *  createCircularReveal方法根据5个参数来创建一个RevealAnimator动画对象。
 *    这五个参数分别是：
 *      view 操作的视图
 *      centerX 动画开始的中心点X
 *      centerY 动画开始的中心点Y
 *      startRadius 动画开始半径
 *      startRadius 动画结束半径
 */
public class OptionsCompatActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ImageView circularRevealImg;

    private List<Integer> images = new ArrayList<>();
    private RecyclerAdapter adapter;
    private GridLayoutManager gridManager;
    private LinearLayoutManager linearMananger;
    private RecyclerAdapter.ItemClickListener clickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_compat);

        findViews();
        setViewData();
        setViewAction();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /**
         * overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
         * startActivity()后调用个方法可定制activity的退出动画和新activity的进入动画
         */
        //TODO yuyang 这样就可以实现activity回退动画
        overridePendingTransition(0, R.anim.slide_bottom_out);
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.options_recycler_view);
        circularRevealImg = (ImageView) findViewById(R.id.options_circular_reveal_img);
    }

    private void setViewData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        clickListener = new RecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(OptionsCompatActivity.this, AdapterDetailActivity.class);
                switch (position){
                    case 0://TODO yuyang makeCustomAnimation
                        ActivityOptionsCompat customOpt = ActivityOptionsCompat.makeCustomAnimation(OptionsCompatActivity.this,
                                R.anim.slide_left_in,R.anim.slide_right_out);
                        ActivityCompat.startActivity(OptionsCompatActivity.this, intent, customOpt.toBundle());
                        /**
                         * 相当于：
                         * startActivity(intent);
                         * overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
                         */
                        break;
                    case 1://TODO yuyang makeScaleUpAnimation
                        View source1 = gridManager.findViewByPosition(position);
                        ActivityOptionsCompat scaleUpOpt = ActivityOptionsCompat.makeScaleUpAnimation(source1,
                                source1.getWidth()/2,source1.getHeight()/2,0,0);
                        ActivityCompat.startActivity(OptionsCompatActivity.this, intent, scaleUpOpt.toBundle());
                        /**
                         * 使用ActivityOptions需要考虑兼容性
                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            startActivity(intent,ActivityOptions.makeScaleUpAnimation(source1,source1.getWidth()/2,source1.getHeight()/2,0,0).toBundle());
                         }*/
                        break;
                    case 2://TODO yuyang makeSceneTransitionAnimation
                        Toast.makeText(getApplicationContext(),"android5.0才会看到transition效果",Toast.LENGTH_SHORT).show();
                        View view1 = gridManager.findViewByPosition(position);
                        ActivityOptionsCompat sceneOpt1 = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                OptionsCompatActivity.this,view1,getResources().getString(R.string.transition_view_option));
                        ActivityCompat.startActivity(OptionsCompatActivity.this, intent, sceneOpt1.toBundle());
                        break;
                    case 3://TODO yuyang makeSceneTransitionAnimation
                        Toast.makeText(getApplicationContext(),"android5.0才会看到transition效果",Toast.LENGTH_SHORT).show();
                        View view2 = gridManager.findViewByPosition(position);
                        Pair pair1 = Pair.create(view2,getResources().getString(R.string.transition_view_option));
                        Pair pair2 = Pair.create(circularRevealImg,getResources().getString(R.string.transition_view_option));
                        ActivityOptionsCompat sceneOpt2 = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                OptionsCompatActivity.this,pair1,pair2);
                        ActivityCompat.startActivity(OptionsCompatActivity.this, intent, sceneOpt2.toBundle());
                        break;
                    case 4://TODO yuyang makeThumbnailScaleUpAnimation
                        View source2 = gridManager.findViewByPosition(position);
                        Bitmap thumb = BitmapFactory.decodeResource(getResources(),R.mipmap.img1);
                        ActivityOptionsCompat thumbnailScaleOpt = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(
                                source2,thumb,source2.getWidth()/2,source2.getHeight()/2);
                        ActivityCompat.startActivity(OptionsCompatActivity.this, intent, thumbnailScaleOpt.toBundle());
                        break;
                    default:
                        break;
                }
            }
        };
        images.add(R.mipmap.img1);
        images.add(R.mipmap.img1);
        images.add(R.mipmap.img1);
        images.add(R.mipmap.img1);
        images.add(R.mipmap.img1);
        images.add(R.mipmap.img1);
        adapter = new RecyclerAdapter(images, clickListener);
        gridManager = new GridLayoutManager(this,2);
        linearMananger = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(gridManager);
        recyclerView.setAdapter(adapter);
    }

    private void setViewAction() {
        circularRevealImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Animator animator = ViewAnimationUtils.createCircularReveal(
                            circularRevealImg,
                            circularRevealImg.getWidth()/2,
                            circularRevealImg.getHeight()/2,
                            circularRevealImg.getWidth(),
                            0);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(2000);
                    animator.start();
                }else {
                    Snackbar.make(circularRevealImg,"圆形揭示效果，android5.0才能实现",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
