package com.yuyang.fitsystemwindowstestdrawer.viewPager.customParallaxViewPager;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * ViewPager页面元素动画效果（小红书动画效果）
 */

public class ParallaxViewPagerActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView imageView;
    private ParallaxContainer parallaxContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //!--yuyang  卧槽，AppCompatActivity类把setFactory方法用了，之后在用的就不起作用了，所以这里需要在super.onCreate之前我们先下手为强
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            int[] attrIds = { R.attr.a_in, R.attr.a_out, R.attr.x_in, R.attr.x_out, R.attr.y_in, R.attr.y_out};
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                AppCompatDelegate delegate = getDelegate();
                View view = delegate.createView(parent, name, context, attrs);

                if (view != null) {
                    TypedArray a = context.obtainStyledAttributes(attrs, attrIds);
                    if (a != null) {
                        if (a.length() > 0) {
                            ParallaxViewTag tag = new ParallaxViewTag();
                            tag.alphaIn = a.getFloat(0, 0f);
                            tag.alphaOut = a.getFloat(1, 0f);
                            tag.xIn = a.getFloat(2, 0f);
                            tag.xOut = a.getFloat(3, 0f);
                            tag.yIn = a.getFloat(4, 0f);
                            tag.yOut = a.getFloat(5, 0f);
                            view.setTag(R.id.id_parallax_view_tag, tag);
                        }
                        a.recycle();
                    }
                }
                return view;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax_view_pager);

        setToolbar();

        imageView = (ImageView) findViewById(R.id.image_view);
        parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container);
        parallaxContainer.setLooping(false);
        parallaxContainer.setManImageView(imageView);
        parallaxContainer.setChildrens(getLayoutInflater(), R.layout.layout_view_intro_1,
                R.layout.layout_view_intro_2,R.layout.layout_view_intro_3,R.layout.layout_view_intro_4,
                R.layout.layout_view_intro_5,R.layout.layout_view_intro_6);
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("ViewPager欢迎页");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
