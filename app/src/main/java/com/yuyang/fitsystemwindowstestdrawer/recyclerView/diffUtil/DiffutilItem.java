package com.yuyang.fitsystemwindowstestdrawer.recyclerView.diffUtil;

/**
 * Created by yuyang on 16/10/12.
 */
public class DiffutilItem implements Cloneable {
    private String title;
    private int imgId;
    private String des;

    public DiffutilItem() {
    }

    public DiffutilItem(String title, int imgId, String des) {
        this.title = title;
        this.imgId = imgId;
        this.des = des;
    }

    //仅写DEMO 用 实现克隆方法
    @Override
    public DiffutilItem clone() throws CloneNotSupportedException {
        DiffutilItem bean = null;
        try {
            bean = (DiffutilItem) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
