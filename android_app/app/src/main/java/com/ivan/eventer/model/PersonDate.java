package com.ivan.eventer.model;

public class PersonDate {

    private String mName;
    private String mEmail;
    private String mAge;
    private String mCity;

    public PersonDate(String name, String email, String age, String city) {

        mName = name;
        mEmail = email;
        mAge = age;
        mCity = city;

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

    public void deletePerson(){

        mName = "";
        mEmail = "";
        mAge = "";
        mCity = "";

    }

    public void updatePerson(String name, String age, String city){

        mName = name;
        mAge = age;
        mCity = city;

    }

}
