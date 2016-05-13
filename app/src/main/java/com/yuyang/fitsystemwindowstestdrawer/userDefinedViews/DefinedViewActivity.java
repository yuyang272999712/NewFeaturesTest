package com.yuyang.fitsystemwindowstestdrawer.userDefinedViews;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedViews.flowLayout.FlowLayout;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedViews.flowLayout.TagAdapter;
import com.yuyang.fitsystemwindowstestdrawer.userDefinedViews.flowLayout.TagFlowLayout;

import java.util.Set;

/**
 * 自定义ViewDragHelper的布局
 * 自定义FlowLayout的流失布局
 * 自定义带清楚按钮的EditText的View
 * CountDownTimer定时工具的使用
 */
public class DefinedViewActivity extends AppCompatActivity {
    private Button timerButton;
    private Button nextButton;
    /**
     * TODO yuyang 可以添加选中动作的FlowLayout
     */
    private TagFlowLayout tagFlowLayout;
    /**
     * TODO yuyang android提供的计时工具
     */
    private CountDownTimer countDownTimer;
    /**
     * TagFlowLayout 的适配器
     */
    private TagAdapter<String> adapter;

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defind_view_test);

        findViews();
        initDatas();
        initAction();
    }

    private void findViews() {
        timerButton = (Button) findViewById(R.id.button);
        nextButton = (Button) findViewById(R.id.defind_next_drag_helper);
        tagFlowLayout = (TagFlowLayout) findViewById(R.id.defind_tag_flow_layout);
    }

    private void initDatas() {
        //按键倒计时
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerButton.setClickable(false);
                timerButton.setText("倒计时" + millisUntilFinished/1000 + "秒");
            }

            @Override
            public void onFinish() {
                timerButton.setClickable(true);
                timerButton.setText("倒计时完成");
            }
        };

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
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.start();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DefinedViewActivity.this, DefinedViewActivity2.class);
                startActivity(intent);
            }
        });

        /**
         * TagFlowLayout的单击事件
         */
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                Toast.makeText(DefinedViewActivity.this, mVals[position], Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        /**
         * TagFlowLayout的选中／放弃选中动作
         */
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener()
        {
            @Override
            public void onSelected(Set<Integer> selectPosSet)
            {
                Toast.makeText(DefinedViewActivity.this, "选中了"+selectPosSet.size()+"个", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
