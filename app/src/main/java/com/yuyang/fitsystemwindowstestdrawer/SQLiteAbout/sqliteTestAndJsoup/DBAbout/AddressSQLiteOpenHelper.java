package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.DBAbout;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yuyang on 16/7/27.
 */
public class AddressSQLiteOpenHelper extends SQLiteOpenHelper {
    //省表
    private final String PROVINCE_CREATE = "create table Province( " +
            "id integer primary key autoincrement, " +
            "spell text, " + //拼音
            "name text" + //中文
            ")";
    //市表
    private final String CITY_CREATE = "create table City( " +
            "id integer primary key autoincrement, " +
            "province_spell text, " + //对应的省ID
            "spell text, " + //拼音
            "name text" + //中文
            ")";
    //县表
    private final String COUNTY_CREATE = "create table County( " +
            "id integer primary key autoincrement, " +
            "city_spell text, " + //对应的市ID
            "spell text, " + //拼音
            "name text" + //中文
            ")";

    public AddressSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PROVINCE_CREATE);
        db.execSQL(CITY_CREATE);
        db.execSQL(COUNTY_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
