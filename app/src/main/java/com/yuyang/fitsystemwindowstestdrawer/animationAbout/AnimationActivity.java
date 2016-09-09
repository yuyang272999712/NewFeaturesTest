package com.yuyang.fitsystemwindowstestdrawer.animationAbout;

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
import com.yuyang.fitsystemwindowstestdrawer.animationAbout.coinWalletAnimator.CoinWalletAnimatorActivity;
import com.yuyang.fitsystemwindowstestdrawer.animationAbout.customAnim.CustomAnimationActivity;
import com.yuyang.fitsystemwindowstestdrawer.animationAbout.shoppingCartAnimator.ShoppingCartActivity;
import com.yuyang.fitsystemwindowstestdrawer.animationAbout.svg.SVGdrawableActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 动画相关
 */
public class AnimationActivity extends AppCompatActivity {
    private List<String> items = Arrays.asList("属性动画","属性动画xml","布局动画","View animate方法动画",
            "视图动画","逐帧动画","自定义动画","SVG动画","钱币动画","购物车动画");

    private Toolbar toolbar;
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
                        intent = new Intent(AnimationActivity.this, PropertyAniActivity.class);
                        break;
                    case 1:
                        intent = new Intent(AnimationActivity.this, PropertyAniXmlActivity.class);
                        break;
                    case 2:
                        intent = new Intent(AnimationActivity.this, LayoutTransitionActivity.class);
                        break;
                    case 3:
                        intent = new Intent(AnimationActivity.this, ViewAnimateActivity.class);
                        break;
                    case 4:
                        intent = new Intent(AnimationActivity.this, ViewAnimationActivity.class);
                        break;
                    case 5:
                        intent = new Intent(AnimationActivity.this, AnimationDrawableActivity.class);
                        break;
                    case 6:
                        intent = new Intent(AnimationActivity.this, CustomAnimationActivity.class);
                        break;
                    case 7:
                        intent = new Intent(AnimationActivity.this, SVGdrawableActivity.class);
                        break;
                    case 8:
                        intent = new Intent(AnimationActivity.this, CoinWalletAnimatorActivity.class);
                        break;
                    case 9:
                        intent = new Intent(AnimationActivity.this, ShoppingCartActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("动画相关");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
