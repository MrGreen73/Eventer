package com.ivan.eventer.model;

/**
 * Created by user on 05.05.2018.
 */
public class Event {
    private String mID;
    private String mTitle;
    private String mDescribe;
    private String mAuthor;
    private byte[] mImage;
    private String mKind;
    private String mTime;
    private String mPlace;
    private String mDate;

    public Event() {
    }

    public Event(String ID, String title, String describe, String author) {
        mID = ID;
        mTitle = title;
        mDescribe = describe;
        mAuthor = author;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public void setDate(String date) {
       mDate=date;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public void setKind(String Kind) {
        mKind = Kind;
    }

    public String getKind() {
        return mKind;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public String getPlace() {
        return mPlace;
    }


    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] mImage) {
        this.mImage = mImage;
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
}
