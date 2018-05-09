package com.ivan.eventer.model;

/**
 * Created by user on 09.05.2018.
 */
public class User {

    private String mId;
    private String mName;
    private String mEmail;
    private String mAge;
    private String mCity;
    private String mPassword;
    private byte[] mImage;

    User() {
    }

    public User(String name, String email, String age, String city, String password, byte[] arr) {
        mName = name;
        mEmail = email;
        mAge = age;
        mCity = city;
        mPassword = password;
        mImage = arr;
    }

    public void setAge(String age) {
        mAge = age;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setImage(byte[] image) {
        mImage = image;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getPassword() {
        return mPassword;
    }

    public byte[] getImage() {
        return mImage;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }


    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getAge() {
        return mAge;
    }

    public String getCity() {
        return mCity;
    }

    public String getEmail() {
        return mEmail;
    }

}
