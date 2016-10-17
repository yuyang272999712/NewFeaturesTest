package com.yuyang.fitsystemwindowstestdrawer.viewPager.gridViewPager;

/**
 * Grid每个item的内容
 */

public class GridItem {
    private String title;
    private int imgId;

    public GridItem() {
    }

    public GridItem(String title, int imgId) {
        this.title = title;
        this.imgId = imgId;
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
}
