package com.ivan.eventer.model;

/**
 * Created by ivan on 28.04.18.
 */

public class Event {

    private String mTitle;
    private String mDescribe;
    private String mAuthor;

    public Event(String title, String describe, String author) {
        mTitle = title;
        mDescribe = describe;
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescribe() {
        return mDescribe;
    }

    public void setDescribe(String describe) {
        mDescribe = describe;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }
}
