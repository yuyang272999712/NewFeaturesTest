package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.bean;

import java.io.Serializable;

/**
 * 县类
 */
public class County extends AddressBaseBean implements Serializable {
    private int id;
    private String city_spell;

    public County() {
        super();
    }

    public County(int id, String city_spell, String spell, String name) {
        super(spell, name);
        this.id = id;
        this.city_spell = city_spell;
    }

    public String getCity_spell() {
        return city_spell;
    }

    public void setCity_spell(String city_spell) {
        this.city_spell = city_spell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
