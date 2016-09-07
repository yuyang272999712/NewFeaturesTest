package com.yuyang.fitsystemwindowstestdrawer.androidL.coordinatorLayoutAbout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 知识点：
 *
 * 一、CollapsingToolbarLayout
 *      CollapsingToolbarLayout可实现toolbar的塌陷效果，
 *      CollapsingToolbarLayout继承自FrameLayout，其 必需 配合AppBarLayout使用（源码中只有父类是AppBarLayout时才会给滚动监听赋值）
 *      一旦脱离AppBarLayout布局使用它，就与普通的FrameLayout一样。
 *      CollapsingToolbarLayout配合AppBarLayout使用时同样必需设置“app:layout_scrollFlags="scroll|enterAlways"”属性，否则不会出现滚动效果
 *
 *    特定属性：
 *      app:contentScrim - 设置当完全CollapsingToolbarLayout折叠(收缩)后的背景颜色(!!!如果不设置将会看到toolbar下面的内容)。
 *      app:statusBarScrim - 只有在5.0及以上系统才有效，从字面意思看是设置statesBar颜色
 *      app:expandedTitleMarginStart - 设置扩张时候(还没有收缩时)title向左填充的距离。
 *      app:expandedTitleTextAppearance - 设置展开时标题的字体样式
 *
 *    其子View可设定的特定属性：
 *      app:layout_collapseMode (折叠模式) - 有两个值:（不设置时默认为滚出效果）
 *          pin -  设置为这个模式时，当CollapsingToolbarLayout完全收缩后，Toolbar还可以保留在屏幕上。
 *          parallax - 设置为这个模式时，在内容滚动时，CollapsingToolbarLayout中的View（比如ImageView)也可以同时滚动，实现视差滚动效果，通常和layout_collapseParallaxMultiplier(设置视差因子)搭配使用。
 *      app:layout_collapseParallaxMultiplier(视差因子) - 设置视差滚动因子，值为：0~1。
 *    （要想toolbar固定在头部不被滑出，则必需设定折叠模式为pin）
 *
 * 二、CoordinatorLayout
 *      layout_anchor 的属性，连同 layout_anchorGravity 一起，可以用来放置与其他视图关联在一起的悬浮视图（如本例中的button）
 *    相关属性：
 *      app:layout_anchor - 关联对象
 *      app:layout_anchorGravity - 与对象的相对关系
 *
 * 三、FloatingActionButton
 *      这是一个浮动按钮。由于FloatingActionButton是重写ImageView的，
 *      所有FloatingActionButton拥有ImageView的一切属性。
 *
 *      属性介绍：
 *          app:backgroundTint : FAB的背景色。
 *          app:elevation      ：FAB的阴影效果。
 *          app:rippleColor    ：设置涟漪的颜色，默认是由背景色生成的暗色调，可以自己指定。
 *          app:pressedTranslationZ  ：FAB动画效果，在它被按下的时候阴影就会增大。
 *
 * 四、CardView
 *      就是一个卡片样式的FrameLayout。
 *
 *      参数介绍：
 *          app:cardBackgroundColor  :   背景颜色
 *          app:cardCornerRadius    ：   设置圆角。
 *          app:cardElevation       ：    阴影。
 *          app:cardMaxElevation    ：       最大阴影。
 *          app:cardPreventCornerOverlap  ： 在v20和之前的版本中添加内边距，这个属性是为了防止卡片内容和边角的重叠。
 *          app:cardUseCompatPadding  ：  设置内边距，v21+的版本和之前的版本仍旧具有一样的计算方式
 *
 */
public class MaterialDesignActivity2 extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design_2);
        findView();
        initView();
        initAction();
    }

    private void findView() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.material_collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        collapsingToolbarLayout.setTitle("开发测试——title");
    }

    private void initAction() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
