package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteOpenHelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;

/**
 * 使用Android提供的SQLiteOpenHelper类管理数据库
 */
public class SQLiteOpenHelperActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button createDbBtn;

    private MySQLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_open_helper);

        findViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //创建数据库
        dbHelper = new MySQLiteOpenHelper(this, "BookStore.db", null, 0);
        createDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();//如果数据库已存在则直接打开，否则创建一个新的数据库
            }
        });
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        createDbBtn = (Button) findViewById(R.id.sqlite_create_database);
    }
}
