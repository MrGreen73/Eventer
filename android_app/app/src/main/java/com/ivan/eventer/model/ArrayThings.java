package com.ivan.eventer.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 11.05.2018.
 */
public class ArrayThings implements Serializable {

    private ArrayList<Thing> arr = new ArrayList<>();

    public ArrayList<Thing> getArr() {
        return arr;
    }

    public void setArr(ArrayList<Thing> arr) {
        this.arr = arr;
    }
}
