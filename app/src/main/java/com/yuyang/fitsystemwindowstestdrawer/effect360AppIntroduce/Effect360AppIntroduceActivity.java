package com.yuyang.fitsystemwindowstestdrawer.effect360AppIntroduce;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 通过自定义Layout和系统Layout模拟360软件介绍
 */
public class Effect360AppIntroduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        Button button1 = new Button(this);
        button1.setText("自定义Layout模拟360软件介绍");
        Button button2 = new Button(this);
        button2.setText("使用现有布局模拟360软件介绍");
        Button button3 = new Button(this);
        button3.setText("通过实现NestedScrollingParent");
        linearLayout.addView(button1);
        linearLayout.addView(button2);
        linearLayout.addView(button3);

        setContentView(linearLayout);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Effect360AppIntroduceActivity.this, AppIntroduce360Activity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Effect360AppIntroduceActivity.this, AppIntroduceActivity.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Effect360AppIntroduceActivity.this, AppIntroduce360NestedScrollActivity.class);
                startActivity(intent);
            }
        });
    }

}
