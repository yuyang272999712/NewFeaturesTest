package com.yuyang.db.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yuyang.db.bean.DaoMaster;
import com.yuyang.db.bean.UserDao;

/**
 * 数据升级使用。!--yuyang 如果有列名改变而又想保存数据，请自行修改 MigrationHelper 中 restoreData 方法
 */

public class MyUpdateOpenHelper extends DaoMaster.OpenHelper {
    public MyUpdateOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyUpdateOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, UserDao.class);//!--yuyang 哪个类升级传那个类
    }

}
