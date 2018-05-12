package com.ivan.eventer.model;

import java.io.Serializable;

/**
 * Created by user on 11.05.2018.
 */
public class Thing implements Serializable {
    /*   ArrayList<String> things = new ArrayList<>();

       public void setThings(ArrayList<String> things) {
           this.things = things;
       }*/
    private String mTitle;

    private boolean mValue;

    public Thing() {
    }

    public Thing(String title) {
        mTitle = title;
        mValue = false;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean getValue() {
        return mValue;
    }

    public void setValue(boolean value) {
        mValue = value;
    }
}
