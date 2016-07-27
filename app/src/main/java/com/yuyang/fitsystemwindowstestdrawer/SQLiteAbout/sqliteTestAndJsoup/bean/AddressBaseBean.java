package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.bean;

import java.io.Serializable;

/**
 * Created by yuyang on 16/7/27.
 */
public abstract class AddressBaseBean implements Serializable {
    protected String spell;
    protected String name;

    public AddressBaseBean(String spell, String name) {
        this.spell = spell;
        this.name = name;
    }

    public AddressBaseBean() {
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
