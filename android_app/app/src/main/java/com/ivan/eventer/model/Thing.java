package com.ivan.eventer.model;


public class Thing {
    String name;
    boolean checkbox;
    String id;

    public Thing() {
         /*Empty Constructor*/
    }

    public Thing(String country, boolean status, String id) {
        this.id = id;

        this.name = country;
        this.checkbox = status;
    }

    //Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
