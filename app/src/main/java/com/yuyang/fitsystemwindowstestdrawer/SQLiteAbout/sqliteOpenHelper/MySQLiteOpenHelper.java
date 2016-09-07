package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yuyang.fitsystemwindowstestdrawer.utils.LogUtils;

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
//            + ",category_id integer" //ZHU yuyang 数据库版本3需要在book表新加字段category_id
            +")";
    private static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name test, "
            + "category_code integer"
            +")";

    //构造函数参数含义：context，name-数据库名称，factory，version-数据库版本号（更新数据库时有用）
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.e("创建了Book表");
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
    }

    //升级逻辑
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.e("升级了Book、Category表");
        //TODO yuyang 注意：这里的case都没加break，这是为了保证在跨版本升级的时候，每一次的数据库修改都能被全部执行到
        //                  比如用户直接从版本一升级到版本三的应用，那么case 1和case 2中的逻辑都会执行
        switch (oldVersion){
            case 1://ZHU yuyang 数据库版本2需要在新增表Category表
                db.execSQL(CREATE_CATEGORY);
            case 2://ZHU yuyang 数据库版本3需要在book表新加字段category_id
                db.execSQL("alter table Book add column category_id integer");
            default:
        }
    }
}
