package com.yuyang.fitsystemwindowstestdrawer.cardViewPager;

import java.io.Serializable;

/**
 * Created by yuyang on 16/3/11.
 */
public class CardPager implements Serializable {
    private int imgId;
    private String title;
    private String describe;

    public CardPager(int imgId, String title, String describe) {
        this.imgId = imgId;
        this.title = title;
        this.describe = describe;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
