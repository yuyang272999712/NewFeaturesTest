package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.bean;

import java.io.Serializable;

/**
 * 市类
 */
public class City extends AddressBaseBean implements Serializable {
    private int id;
    private String province_spell;

    public City() {
        super();
    }

    public City(int id, String province_spell, String spell, String name) {
        super(spell, name);
        this.id = id;
        this.province_spell = province_spell;
    }

    public String getProvince_spell() {
        return province_spell;
    }

    public void setProvince_spell(String province_spell) {
        this.province_spell = province_spell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
