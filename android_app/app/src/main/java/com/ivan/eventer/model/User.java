package com.ivan.eventer.model;

/**
 * Created by ivan on 17.04.18.
 */

public class User {

    private String mId;
    private String mName;
    private String mAge;
    private String mCity;

    public User(String id, String name, String age, String city) {
        mId = id;
        mName = name;
        mAge = age;
        mCity = city;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAge() {
        return mAge;
    }

    public void setAge(String age) {
        mAge = age;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }
}
