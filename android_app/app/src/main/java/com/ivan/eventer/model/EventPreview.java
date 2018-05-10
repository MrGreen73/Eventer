package com.ivan.eventer.model;

public class EventPreview {

    private String mID;
    private String mTitle;
    private String mDescribe;
    private String mAuthor;
    private byte[] mImage;
    private String mKind;
    private String mTime;
    private String mPlace;
    private String mDate;

    public EventPreview() {

    }

    public EventPreview(String ID, String title, String describe, String author, byte[] image, String kind, String time, String place, String date) {
        mID = ID;
        mTitle = title;
        mDescribe = describe;
        mAuthor = author;
        mImage = image;
        mKind = kind;
        mTime = time;
        mPlace = place;
        mDate = date;
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

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        mImage = image;
    }

    public String getKind() {
        return mKind;
    }

    public void setKind(String kind) {
        mKind = kind;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }
}
