package com.yuyang.fitsystemwindowstestdrawer.googleWidget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;
import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.googleWidget.customFlexboxLayout.TagAdapter;
import com.yuyang.fitsystemwindowstestdrawer.googleWidget.customFlexboxLayout.TagFlowLayout;

import java.util.Set;

/**
 * FlexboxLayout类似于FlowLayout的流式布局
 *  自定义属性：
 *      app:flexDirection="column" //子控件排列方式，是列或行（可倒序排列）
 *      app:flexWrap="wrap"  // 子控件是否自动换行
 *      app:alignItems="flex_start"  // 子控件在其所在行/列中的对齐方式
 *      app:alignContent="flex_start" //内容的对齐方式 从头对齐还是从尾对齐
 *      app:justifyContent="flex_end" //子控件行列时的对齐方式，中间或两端
 *  子View可用属性：
 *      app:layout_order="1" //数字越小该view排列越考前
 *      app:layout_flexGrow="2" //比重，相当于layout_weight属性
 *      app:layout_flexShrink="" //未知
 *      app:layout_flexBasisPercent="20" //占父控件的百分比
 *      app:layout_alignSelf="baseline" //该view在此行／列的相对位置
 */
public class FlexboxLayoutActivity extends AppCompatActivity {
    private TagFlowLayout flowLayout;

    /**
     * TagFlowLayout 的适配器
     */
    private TagAdapter<String> adapter;

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flex_box);

        flowLayout = (TagFlowLayout) findViewById(R.id.flex_tag_layout);

        final LayoutInflater mInflater = LayoutInflater.from(this);
        adapter = new TagAdapter<String>(mVals) {
            @Override
            public View getView(ViewGroup parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.flow_tag_item, flowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        flowLayout.setAdapter(adapter);

        /**
         * TagFlowLayout的单击事件
         */
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlexboxLayout parent) {
                Toast.makeText(FlexboxLayoutActivity.this, mVals[position], Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        /**
         * TagFlowLayout的选中／放弃选中动作
         */
        flowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                Toast.makeText(FlexboxLayoutActivity.this, "选中了"+selectPosSet.size()+"个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
