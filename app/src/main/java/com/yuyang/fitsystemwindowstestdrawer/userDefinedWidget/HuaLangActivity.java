package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.galleryEffect.HorizontalScrollViewAdapter;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.galleryEffect.MyHorizontalScrollView;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.galleryEffect.RecyclerGalleryAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 画廊效果实现，google不再推荐使用Gallery，
 * 推荐使用ViewPager和HorizontalScrollView来实现Gallery的效果
 *
 * 这里主要使用HorizontalScrollView以及自定义HorizontalScrollView实现
 */
public class HuaLangActivity extends AppCompatActivity {
    private LinearLayout mGallery;
    private List<Integer> mImgIds;
    private LayoutInflater inflater;

    private MyHorizontalScrollView mHorizontalScrollView;
    private HorizontalScrollViewAdapter mAdapter;
    private ImageView mImg;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerGalleryAdapter recyclerGalleryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hua_lang);
        inflater = LayoutInflater.from(this);

        findViews();
        initAction();
        initDatas();
    }

    private void findViews() {
        mGallery = (LinearLayout) findViewById(R.id.hualang_gallery);
        mHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.hualang_horizontalScrollView);
        mImg = (ImageView) findViewById(R.id.hualang_img);
        recyclerView = (RecyclerView) findViewById(R.id.hualang_recycler_view);
    }

    private void initDatas() {
        mImgIds = new ArrayList<>(Arrays.asList(
                R.mipmap.a, R.mipmap.b, R.mipmap.c,
                R.mipmap.d, R.mipmap.e, R.mipmap.f,
                R.mipmap.g, R.mipmap.h, R.mipmap.l));
        for (int i=0; i<mImgIds.size(); i++){
            View view = inflater.inflate(R.layout.item_4_gallery, mGallery, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.gallery_item_image);
            imageView.setImageResource(mImgIds.get(i));
            TextView textView = (TextView) view.findViewById(R.id.gallery_item_text);
            textView.setText("Some info "+i);
            mGallery.addView(view);
        }

        mAdapter = new HorizontalScrollViewAdapter(this, mImgIds);
        //设置适配器
        mHorizontalScrollView.initDatas(mAdapter);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerGalleryAdapter = new RecyclerGalleryAdapter(mImgIds);
        recyclerView.setAdapter(recyclerGalleryAdapter);
    }

    private void initAction() {
        //添加滚动回调
        mHorizontalScrollView
                .setCurrentImageChangeListener(new MyHorizontalScrollView.CurrentImageChangeListener()
                {
                    @Override
                    public void onCurrentImgChanged(int position,
                                                    View viewIndicator)
                    {
                        mImg.setImageResource(mImgIds.get(position));
                        viewIndicator.setBackgroundColor(Color.parseColor("#AA024DA4"));
                    }
                });
        //添加点击回调
        mHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener()
        {

            @Override
            public void onClick(View view, int position)
            {
                mImg.setImageResource(mImgIds.get(position));
                view.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });
    }
}
