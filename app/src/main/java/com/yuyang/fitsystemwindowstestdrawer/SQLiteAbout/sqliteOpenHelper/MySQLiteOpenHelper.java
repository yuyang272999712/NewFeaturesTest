package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite数据库帮助类
 * SQLite提供的数据类型：integer - 整型
 *                    real - 浮点型
 *                    text - 文本类型
 *                    blob - 二进制类型
 * SQLiteOpenHelper提供了两个实例方法来打开数据库：
 *  1、getReadableDatabase()
 *  2、getWritableDatabase()
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    /** 创建Book数据表 */
    private static final String CREATE_BOOK = "create table Book ("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text"
            +")";

    //构造函数参数含义：context，name-数据库名称，factory，version-数据库版本号（更新数据库时有用）
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
    }

    //升级逻辑
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
