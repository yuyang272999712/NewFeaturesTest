package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.flowLayout.FlowLayout;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.flowLayout.TagAdapter;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.flowLayout.TagFlowLayout;

import java.util.Set;

/**
 * 自定义流式布局
 */
public class FlowLayoutActivity extends AppCompatActivity {
    /**
     * TODO yuyang 可以添加选中动作的FlowLayout
     */
    private TagFlowLayout tagFlowLayout;
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
        setContentView(R.layout.activity_flow_layout);

        findViews();
        initDatas();
        initAction();
    }

    private void findViews() {
        tagFlowLayout = (TagFlowLayout) findViewById(R.id.defind_tag_flow_layout);
    }

    private void initDatas() {
        final LayoutInflater mInflater = LayoutInflater.from(this);
        adapter = new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.flow_tag_item,
                        tagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        tagFlowLayout.setAdapter(adapter);
    }

    private void initAction() {
        /**
         * TagFlowLayout的单击事件
         */
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(FlowLayoutActivity.this, mVals[position], Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        /**
         * TagFlowLayout的选中／放弃选中动作
         */
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                Toast.makeText(FlowLayoutActivity.this, "选中了"+selectPosSet.size()+"个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
