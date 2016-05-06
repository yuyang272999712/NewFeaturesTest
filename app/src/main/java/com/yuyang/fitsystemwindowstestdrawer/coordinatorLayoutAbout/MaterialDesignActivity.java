package com.yuyang.fitsystemwindowstestdrawer.coordinatorLayoutAbout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 知识点：
 * 一、Snackbar 只有使用CoordinatorLayout作为基本布局，FAB按钮才会自动产生向上移动的动画。
 *    FAB浮动操作按钮有一个 默认的 behavior来检测Snackbar的添加并让按钮在Snackbar之上呈现上移与Snackbar等高的动画。
 *
 * 二、CoordinatorLayout （[kəʊ'ɔ:dɪneɪtə] ）协调员
 *    CoordinatorLayout自己并不控制View，所有的控制权都在Behavior
 *      该布局会检测他的所有子View，传递Behavior事件。
 *      该布局类似于FrameLayout布局，普图部件可以通过设置“android:layout_gravity="bottom|right"”来设置在布局中的位置
 *
 * 三、AppBarLayout
 *    CoordinatorLayout配合AppBarLayout布局使用可以实现滚动响应事件 （AppBarLayout布局必需是CoordinatorLayout布局中的第一个）
 *    （AppBarLayout的父类是LinearLayout竖直排版,即：AppBarLayout中可以竖直排版多个子View，
 *    但是如果第一个子View的app:layout_behavior属性没用设置“scroll”，那么下面的子View也不会滚动出屏幕,
 *    !!!AppBarLayout不必非要配合toolbar使用，任何放在AppBarLayout中的子View都可以实现滚动）。
 *
 *      CoordinatorLayout中第二个控件设置“app:layout_behavior="@string/appbar_scrolling_view_behavior"”属性即可自动排版到AppBarLayout布局之下。
 *
 *      AppBarLayout包含的控件要想有滚动效果必须做到如下三点：
 *          1. CoordinatorLayout作为布局的父布局容器。
 *          2. 给需要滑动的组件设置 app:layout_scrollFlags=”scroll|enterAlways” 属性，所有想滚动出屏幕的view都需要设置scroll值。
 *          3. 给滑动的组件设置app:layout_behavior属性
 *
 *      “app:layout_scrollFlags”属性值有如下五种：
 *          1. scroll: 所有想滚动出屏幕的view都需要设置这个flag， 没有设置这个flag的view将被固定在屏幕顶部。例如，Toolbar 没有设置这个值，将会停留在屏幕顶部。
 *          2. enterAlways: 设置这个flag时，向下的滚动都会导致该view变为可见，启用快速“返回模式”。
 *          （以下三个值主要配合CollapsingToolbarLayout使用效果）
 *          3. enterAlwaysCollapsed: 当你的视图已经设置minHeight属性又使用此标志时，你的视图只能已最小高度进入，只有当滚动视图到达顶部时才扩大到完整高度。
 *          4. exitUntilCollapsed: 滚动退出屏幕，最后折叠在顶端。
 *          5. snap：滚动压缩到50%以后自动收缩，不到则会自动复原
 *
 * 四、CollapsingToolbarLayout
 *    AppBarLayout配合CollapsingToolbarLayout布局使用可以是toolbar具有塌缩效果（详见MaterialDesignActivity2.class页面）
 *
 * 五、Behavior
 *    通过 CoordinatorLayout.Behavior(YourView.Behavior.class) 来定义自己的Behavior，
 *    并在layout 文件中设置 app:layout_behavior=”com.example.app.YourView$Behavior” 来达到效果。
 *
 *    自定义Behavior 需要根据需要重写对应的方法，比如：
 *      public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency)
 *      public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency)
 *      public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes)
 *      public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes)
 *      等方法
 *
 * 六、TabLayout
 *    有以下常用属性：
 *      app:tabGravity="fill"  表示TabLayout中的Tabs要占满屏幕的width；
 *      app:tabSelectedTextColor：Tab被选中字体的颜色；
 *      app:tabTextColor：Tab未被选中字体的颜色；
 *      app:tabIndicatorColor：Tab指示器下标的颜色；
 *      
 * 七、FAB
 *    app:backgroundTint - 设置FAB的背景颜色。
 *    app:rippleColor - 设置FAB点击时的背景颜色。
 *    app:borderWidth - 该属性尤为重要，如果不设置0dp，那么在4.1的sdk上FAB会显示为正方形，而且在5.0以后的sdk没有阴影效果。所以设置为borderWidth="0dp"。
 *    app:elevation - 默认状态下FAB的阴影大小。
 *    app:pressedTranslationZ - 点击时候FAB的阴影大小。
 *    app:fabSize - 设置FAB的大小，该属性有两个值，分别为normal和mini，对应的FAB大小分别为56dp和40dp。
 *    src - 设置FAB的图标，Google建议符合Design设计的该图标大小为24dp。
 *    以下两个属性只有在父控件是CoordinatorLayout时才有用
 *    app:layout_anchor - 设置FAB的锚点，即以哪个控件为参照点设置位置。
 *    app:layout_anchorGravity - 设置FAB相对锚点的位置，值有 bottom、center、right、left、top等。
 */
public class MaterialDesignActivity extends AppCompatActivity {
    FloatingActionButton actionButton;
    Toolbar toolbar;
    Button btn_to_collapsing;
    Button btn_to_behavior;
    Button btn_to_behavior2;
    Button btn_to_swipe_dismiss;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        findView();
        initView();
        initAction();
    }

    private void initView() {
        toolbar.setTitle("开发测试");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void findView() {
        actionButton = (FloatingActionButton) findViewById(R.id.material_action_button);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_to_collapsing = (Button) findViewById(R.id.material_button1);
        btn_to_behavior = (Button) findViewById(R.id.material_button2);
        btn_to_behavior2 = (Button) findViewById(R.id.material_button3);
        btn_to_swipe_dismiss = (Button) findViewById(R.id.btn_to_swipe_dismiss);
    }

    private void initAction() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Snackbar snackbar = Snackbar.make(actionButton,"snackbar测试",Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        });
        btn_to_collapsing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaterialDesignActivity.this, MaterialDesignActivity2.class);
                startActivity(intent);
            }
        });
        btn_to_behavior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaterialDesignActivity.this, BehaviorActivity1.class);
                startActivity(intent);
            }
        });
        btn_to_behavior2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaterialDesignActivity.this, BehaviorActivity2.class);
                startActivity(intent);
            }
        });
        btn_to_swipe_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaterialDesignActivity.this, SwipeDismissBehaviorActivity.class);
                startActivity(intent);
            }
        });
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
