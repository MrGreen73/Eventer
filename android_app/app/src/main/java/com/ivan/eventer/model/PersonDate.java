package com.ivan.eventer.model;

// Класс, хранящий информацию о пользователе
public class PersonDate {

    private String mName; // Имя
    private String mEmail; // Почта
    private String mAge; // Возраст
    private String mCity; // Город
    private byte[] mImage; // Картинка


    public PersonDate(String name, String email, String age, String city, byte[] image) {

        mName = name;
        mEmail = email;
        mAge = age;
        mCity = city;
        mImage = image;

    }

    public PersonDate(){

        deletePerson();

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
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

    public byte[] getImage() {
        return mImage;
    }

    public void setImage(byte[] image) {
        mImage = image;
    }

    public void deletePerson(){

        mName = "";
        mEmail = "";
        mAge = "";
        mCity = "";

    }

    public void updatePerson(String name, String age, String city, byte[] image){

        mName = name;
        mAge = age;
        mCity = city;
        mImage = image;

    }

}
