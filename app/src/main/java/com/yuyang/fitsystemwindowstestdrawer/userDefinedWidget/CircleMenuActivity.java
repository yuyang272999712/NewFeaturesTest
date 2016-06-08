package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.CircleMenu.CircleMenuLayout;

/**
 * 圆形菜单
 */
public class CircleMenuActivity extends AppCompatActivity {
    private CircleMenuLayout mCircleMenuLayout1;
    private CircleMenuLayout mCircleMenuLayout2;

    private String[] mItemTexts = new String[] { "安全中心 ", "特色服务", "投资理财",
            "转账汇款", "我的账户", "信用卡" };
    private int[] mItemImgs = new int[] { R.mipmap.circle_menu_item_1_normal,
            R.mipmap.circle_menu_item_2_normal, R.mipmap.circle_menu_item_3_normal,
            R.mipmap.circle_menu_item_4_normal, R.mipmap.circle_menu_item_5_normal,
            R.mipmap.circle_menu_item_6_normal };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_menu);

        mCircleMenuLayout1 = (CircleMenuLayout) findViewById(R.id.circle_menu_1);
        mCircleMenuLayout2 = (CircleMenuLayout) findViewById(R.id.circle_menu_2);

        mCircleMenuLayout1.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        mCircleMenuLayout1.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                Toast.makeText(CircleMenuActivity.this,
                        mItemTexts[pos], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemCenterClick(View view) {
                Toast.makeText(CircleMenuActivity.this,
                        "点击中央菜单", Toast.LENGTH_SHORT).show();
            }
        });
        mCircleMenuLayout2.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
    }
}
