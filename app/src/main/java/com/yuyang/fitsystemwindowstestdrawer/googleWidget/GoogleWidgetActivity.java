package com.yuyang.fitsystemwindowstestdrawer.googleWidget;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.googleWidget.CompatDivider.DividerActivity;
import com.yuyang.fitsystemwindowstestdrawer.googleWidget.customFlexboxLayout.FlexboxLayoutActivity;
import com.yuyang.fitsystemwindowstestdrawer.googleWidget.dataBinding.DataBindingDemoActivity;
import com.yuyang.fitsystemwindowstestdrawer.googleWidget.rangeSeekBar.SeekBarActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.SourceCodeAnalyzeActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.asyncTask.AsyncTaskSimpleActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.childThreadUpdateUI.ChildThreadUpdateUIActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.drawableAbout.DrawableAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.fragmentAbout.FragmentAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.handlerAndMessage.HandlerAndMessageActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.layoutInflate.LayoutInflateActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.layoutInflateFactory.LayoutInflateFactoryActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.loader.LoaderAboutActivity;
import com.yuyang.fitsystemwindowstestdrawer.sourceCodeAnalyze.touchEventDispatch.ViewTouchEventActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 很少出现但很实用的控件
 *  CountDownTimer 倒计时工具
 *  1、FlexboxLayout
 *  2、divider
 *  3、SeekBar
 *
 */
public class GoogleWidgetActivity  extends AppCompatActivity {
    private List<String> items = Arrays.asList("google流式布局","divider属性及兼容处理","SeekBar",
            "微信自动抢红包","DataBinding");

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
                        intent = new Intent(GoogleWidgetActivity.this, FlexboxLayoutActivity.class);
                        break;
                    case 1:
                        intent = new Intent(GoogleWidgetActivity.this, DividerActivity.class);
                        break;
                    case 2:
                        intent = new Intent(GoogleWidgetActivity.this, SeekBarActivity.class);
                        break;
                    case 3:
                        intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        break;
                    case 4:
                        intent = new Intent(GoogleWidgetActivity.this, DataBindingDemoActivity.class);
                        break;

                }
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("系统控件");
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
/*
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
*/
