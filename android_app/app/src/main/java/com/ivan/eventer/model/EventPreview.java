package com.ivan.eventer.model;

public class EventPreview {

    private String mID;
    private String mTitle;
    private String mCount;
    private String mDescribe;
    private String mPlace;
    private String mAuthor;

    public EventPreview() {

    }

    public EventPreview(String ID, String title, String count, String describe, String place, String author) {
        mID = ID;
        mTitle = title;
        mCount = count;
        mDescribe = describe;
        mPlace = place;
        mAuthor = author;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getCount() {
        return mCount;
    }

    public void setCount(String count) {
        mCount = count;
    }

    public String getDescribe() {
        return mDescribe;
    }

    public void setDescribe(String describe) {
        mDescribe = describe;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }
}
