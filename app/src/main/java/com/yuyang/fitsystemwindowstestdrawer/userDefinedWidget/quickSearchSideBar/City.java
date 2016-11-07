package com.yuyang.fitsystemwindowstestdrawer.userDefinedWidget.quickSearchSideBar;

/**
 * Created by yuyang on 16/11/7.
 */

public class City {
    private int code;
    private String name;
    private int parantCode;

    public City(int code, String name, int parantCode) {
        this.code = code;
        this.name = name;
        this.parantCode = parantCode;
    }

    public City() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParantCode() {
        return parantCode;
    }

    public void setParantCode(int parantCode) {
        this.parantCode = parantCode;
    }
}
