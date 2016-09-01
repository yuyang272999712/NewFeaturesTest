package com.yuyang.fitsystemwindowstestdrawer.softInput.emotionMode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.softInput.emotionMode.utils.GlobalOnItemClickManager;

/**
 * 聊天界面
 */
public class ChatActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editText;//内容输入框
    private ImageView emotionBtn;//显示表情选择框的按钮
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View emotionLayout;//表情选择框布局
    private ListView listView;//内容显示区域

    private EmotionInputDetector detector;//表情布局与软件显示的控制器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        findViews();

        //绑定软键盘与表情布局的显示设置
        detector = EmotionInputDetector.with(this)
                .bindToContent(listView)
                .bindToEditText(editText)
                .bindToEmotionButton(emotionBtn)
                .setEmotionView(emotionLayout);

        viewPager.setAdapter(new EmotionPageAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        //表情布局－表情Item事件绑定
        GlobalOnItemClickManager.getInstance().attachToEditText(editText);
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        editText = (EditText) findViewById(R.id.edit_text);
        emotionBtn = (ImageView) findViewById(R.id.emotion_button);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        emotionLayout = findViewById(R.id.emotion_layout);
        listView = (ListView) findViewById(R.id.list);

        toolbar.setTitle("聊天");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //现隐藏表情布局
        if (!detector.interceptBackPress()) {
            super.onBackPressed();
        }
    }
}
