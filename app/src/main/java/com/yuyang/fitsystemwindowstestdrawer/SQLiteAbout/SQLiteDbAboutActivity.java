package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteOpenHelper.SQLiteOpenHelperActivity;

/**
 * 数据库操作
 */
public class SQLiteDbAboutActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_db_about);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 使用Android提供的SQLiteOpenHelper类
     * @param view
     */
    public void gotoSQLiteOpenHelperActivity(View view){
        Intent intent = new Intent(this, SQLiteOpenHelperActivity.class);
        startActivity(intent);
    }
}
