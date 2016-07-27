package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.DBAbout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yuyang.fitsystemwindowstestdrawer.MyApplication;
import com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.bean.City;
import com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.bean.County;
import com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.bean.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据查询帮助类
 */
public class AddressDBUtils {
    private static final String DBName = "Address.db";
    private static final int version = 1;
    private static final Context context = MyApplication.getInstance().getApplicationContext();
    private static final AddressSQLiteOpenHelper sqLiteOpenHelper = new AddressSQLiteOpenHelper(context, DBName, null, version);

    /**
     * 获取所有省
     * @return
     */
    public static List<Province> getProvince(){
        List<Province> provinces = new ArrayList<>();
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String spell = cursor.getString(cursor.getColumnIndex("spell"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            provinces.add(new Province(id, spell, name));
        }
        cursor.close();
        return provinces;
    }

    /**
     * 保存所有的省份
     * @param provinces
     */
    public static void saveProvinces(List<Province> provinces){
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        for (Province province:provinces){
            ContentValues values = new ContentValues();
            values.put("spell", province.getSpell());
            values.put("name", province.getName());
            db.insert("Province", null, values);
        }
    }

    /**
     * 根据省份获取该省下的所有市
     * @param province_spell
     * @return
     */
    public static List<City> getCityByProvince(String province_spell){
        List<City> cities = new ArrayList<>();
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("City", null, "province_spell=?", new String[]{province_spell}, null, null, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String spell = cursor.getString(cursor.getColumnIndex("spell"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            cities.add(new City(id, province_spell, spell, name));
        }
        cursor.close();
        return cities;
    }

    /**
     * 保存抓取的市信息
     * @param cities
     * @param province_spell
     */
    public static void saveCities(List<City> cities, String province_spell){
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        for (City city:cities){
            ContentValues values = new ContentValues();
            values.put("province_spell", province_spell);
            values.put("spell", city.getSpell());
            values.put("name", city.getName());
            db.insert("City", null, values);
        }
    }

    /**
     * 根据市获取该市下的所有县
     * @param city_spell
     * @return
     */
    public static List<County> getCountyByCity(String city_spell){
        List<County> counties = new ArrayList<>();
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("County", null, "city_spell=?", new String[]{city_spell}, null, null, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String spell = cursor.getString(cursor.getColumnIndex("spell"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            counties.add(new County(id, city_spell, spell, name));
        }
        cursor.close();
        return counties;
    }

    /**
     * 保存所有县
     * @param counties
     * @param city_spell
     */
    public static void saveCounties(List<County> counties, String city_spell){
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        for (County county:counties){
            ContentValues values = new ContentValues();
            values.put("city_spell", city_spell);
            values.put("spell", county.getSpell());
            values.put("name", county.getName());
            db.insert("County", null, values);
        }
    }
}
