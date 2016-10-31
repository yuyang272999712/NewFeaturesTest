package com.yuyang.fitsystemwindowstestdrawer.viewPager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.cardViewPager.CardViewPagerActivity;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.customParallaxViewPager.ParallaxViewPagerActivity;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.elementAnimationViewPager.ElementAnimationActivity;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.galleryViewPager.GalleryViewPagerActivity;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.gridViewPager.GridViewPagerActivity;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.loopViewPager.LoopViewPagerActivity;
import com.yuyang.fitsystemwindowstestdrawer.viewPager.transformsAnimations.TransformsAnimationActivity;

import java.util.Arrays;
import java.util.List;

/**
 * ViewPager应用集合
 */

public class ViewPagerActivity extends AppCompatActivity {
    private List<String> items = Arrays.asList("卡片效果","GridView配合ViewPager","画廊效果",
            "pager切换动画","无限循环的ViewPager","小红书欢迎页","ViewPager切换页面元素进出");

    private Toolbar mToolbar;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_list);

        setToolbar();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_just_text){
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null){
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_just_text, parent, false);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.id_info);
                textView.setText(items.get(position));
                return convertView;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(ViewPagerActivity.this, CardViewPagerActivity.class);
                        break;
                    case 1:
                        intent = new Intent(ViewPagerActivity.this, GridViewPagerActivity.class);
                        break;
                    case 2:
                        intent = new Intent(ViewPagerActivity.this, GalleryViewPagerActivity.class);
                        break;
                    case 3:
                        intent = new Intent(ViewPagerActivity.this, TransformsAnimationActivity.class);
                        break;
                    case 4:
                        intent = new Intent(ViewPagerActivity.this, LoopViewPagerActivity.class);
                        break;
                    case 5:
                        intent = new Intent(ViewPagerActivity.this, ParallaxViewPagerActivity.class);
                        break;
                    case 6:
                        intent = new Intent(ViewPagerActivity.this, ElementAnimationActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("ViewPager应用");
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
