package com.yuyang.fitsystemwindowstestdrawer.contentProvider;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * ContentProvider相关应用
 *
 * URI组成：
 *  权限(authority)+路径(path)
 *  例：com.yuyang.database.provider/table1 (其中权限为：com.yuyang.database.provider，路径为：table1)
 *  UriMatcher中URI可以使用通配符：
 *  1、 *：表示匹配任意长度的任意字符
 *  2、 #：表示匹配任意长度的数字
 *  例：com.yuyang.database.provider/table1/#
 *
 * Uri对象；
 *  getPathSegments()方法可以获取URI权限之后部分的以"/"符号分隔的字符串数组，那么这个数组中第0个位置存放的就是路径，
 *  第1个位置存放的就是Id或其他查询条件
 */
public class ContentProviderActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private Button contactBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        findViews();
        setActions();
    }

    private void setActions() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContentProviderActivity.this, ReadContactsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        contactBtn = (Button) findViewById(R.id.provider_contacts);
    }
}
