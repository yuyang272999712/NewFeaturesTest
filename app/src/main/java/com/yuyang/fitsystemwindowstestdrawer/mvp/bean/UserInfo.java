package com.yuyang.fitsystemwindowstestdrawer.mvp.bean;

/**
 * Created by yuyang on 16/3/1.
 */
public class UserInfo {
    private String name;
    private int age;
    private String info;

    public UserInfo() {
    }

    public UserInfo(String name, int age, String info) {
        this.name = name;
        this.age = age;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
