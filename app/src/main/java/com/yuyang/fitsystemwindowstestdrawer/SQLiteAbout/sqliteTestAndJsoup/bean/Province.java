package com.yuyang.fitsystemwindowstestdrawer.SQLiteAbout.sqliteTestAndJsoup.bean;

/**
 * 省类
 */
public class Province extends AddressBaseBean {
    private int id;

    public Province() {
        super();
    }

    public Province(int id, String spell, String name) {
        super(spell, name);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
