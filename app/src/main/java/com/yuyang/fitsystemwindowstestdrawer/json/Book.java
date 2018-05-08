package com.yuyang.fitsystemwindowstestdrawer.json;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by yuyang on 2018/5/8.
 */

public class Book {
    private String title;
    private float price;
    @SerializedName("date")
    private Date publication_time;

    public Book() {
    }

    public Book(String title, float price, Date date) {
        this.title = title;
        this.price = price;
        this.publication_time = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getPublication_time() {
        return publication_time;
    }

    public void setPublication_time(Date publication_time) {
        this.publication_time = publication_time;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", publication_time=" + publication_time +
                '}';
    }
}
