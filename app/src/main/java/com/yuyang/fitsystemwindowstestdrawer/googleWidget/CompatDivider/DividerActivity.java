package com.yuyang.fitsystemwindowstestdrawer.googleWidget.CompatDivider;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * divider、showDividers、dividerPadding属性介绍：
 *  divider可以设置一个drawable作为元素间的间隔;
 *  showDividers：可取值为：middle(子元素间)、beginning（第一个元素左边）、end（最后一个元素右边）、none；【关于垂直方向的类似】
 *  dividerPadding：设置绘制间隔元素的上下padding。
 */
public class DividerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divider);
    }

    //按钮3
    public void deleteBtn2(View view){
        Button btn2 = (Button) findViewById(R.id.divider_btn2);
        if (btn2.getVisibility() == View.VISIBLE) {
            btn2.setVisibility(View.GONE);
        }else {
            btn2.setVisibility(View.VISIBLE);
        }
    }
}
