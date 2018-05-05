package com.ivan.eventer.model;

import java.util.ArrayList;

/**
 * Created by ivan on 17.04.18.
 */

public class User {

    private Long id;
    private String name;
    private String password;
    private String email;
    private String age;
    private String city;

    public String getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private ArrayList<Long> eventsSub = new ArrayList<>();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEventsSub(ArrayList<Long> eventsSub) {
        this.eventsSub = eventsSub;
    }

    public ArrayList<Long> getEventsSub() {
        return eventsSub;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
