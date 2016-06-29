package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteOpenHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;
import com.yuyang.fitsystemwindowstestdrawer.utils.ToastUtils;

/**
 * 使用Android提供的SQLiteOpenHelper类管理数据库
 *
 * SQLiteDatabase的增删改操作都很简单，查找操作参数比较多
 * TODO yuyang query()方法参数：
 * query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
 *  1、table - from table _name - 指定查询的表名
 *  2、columns - select columns1,columns2 - 指定查询的列名
 *  3、selection - where column = ? - 指定where的约束条件
 *  4、selectionArgs - {"args1","args2"} - 为where中的占位符提供具体的值
 *  5、groupBy - group by column - 指定需要group by的列
 *  6、having - having column = value - 对group by后的结果进一步约束
 *  7、orderBy - order by column1,column2 - 指定查询结果的排序方式
 */
public class SQLiteOpenHelperActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button createDbBtn;
    private Button upDbBtn;
    private Button addDataBtn;
    private Button upDateBtn;
    private Button deleteBtn;
    private Button queryBtn;
    private Button transactionBtn;

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
        dbHelper = new MySQLiteOpenHelper(this, "BookStore.db", null, 1);
        createDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();//如果数据库已存在则直接打开，否则创建一个新的数据库
            }
        });
        //升级数据库
        upDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLiteOpenHelper helper = new MySQLiteOpenHelper(SQLiteOpenHelperActivity.this, "BookStore.db", null, 2);
                ToastUtils.showLong(SQLiteOpenHelperActivity.this, "只能升级一次，点过之后就不能再点创建了");
            }
        });
        //添加数据
        addDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 342);
                values.put("price", 16.34);
                //添加数据
                db.insert("Book", null, values);

                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 46.84);
                db.insert("Book", null, values);
            }
        });
        //更新数据
        upDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                db.update("Book", values, "name=?", new String[]{"The Da Vinci Code"});
            }
        });
        //删除数据
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book", "pages>?", new String[]{"500"});
            }
        });
        //查询数据
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //查询Book表所有数据
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                while (cursor.moveToNext()){
                    //遍历cursor
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String author = cursor.getString(cursor.getColumnIndex("author"));
                    int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                    double price = cursor.getDouble(cursor.getColumnIndex("price"));
                    LogUtils.d("book name is "+name+"\n"+"book author is "+author+"\n"+"book pages is "+pages+"\n"+"book price is "+price);
                }
                cursor.close();
            }
        });
        //事务操作
        transactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                try {
                    //开启事务
                    db.beginTransaction();
                    db.delete("Book", null, null);
                    ContentValues values = new ContentValues();
                    values.put("name", "Game of Thrones");
                    values.put("author", "George Martin");
                    values.put("pages", 720);
                    values.put("price", 20.88);
                    db.insert("Book", null, values);
                    //事务执行成功
                    db.setTransactionSuccessful();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //结束事务
                    db.endTransaction();
                }
            }
        });
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        createDbBtn = (Button) findViewById(R.id.sqlite_create_database);
        upDbBtn = (Button) findViewById(R.id.sqlite_update_database);
        addDataBtn = (Button) findViewById(R.id.sqlite_add_data);
        upDateBtn = (Button) findViewById(R.id.sqlite_update_data);
        deleteBtn = (Button) findViewById(R.id.sqlite_delete_data);
        queryBtn = (Button) findViewById(R.id.sqlite_query_data);
        transactionBtn = (Button) findViewById(R.id.sqlite_transaction);
    }
}
