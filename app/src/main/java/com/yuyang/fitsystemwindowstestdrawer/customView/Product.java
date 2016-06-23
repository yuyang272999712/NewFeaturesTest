package com.yuyang.fitsystemwindowstestdrawer.customView;

/**
 * 用于配合CustomCoordinateAxisView使用
 */
public class Product {
    private String title;
    private float percent;

    public Product(String title, float percent) {
        this.title = title;
        this.percent = percent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
