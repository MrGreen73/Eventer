package com.ivan.eventer.model;

import java.util.ArrayList;

/**
 * Created by ivan on 05.05.18.
 */

public class ListEvents {


  private ArrayList<Event> mListEvent;

  public ListEvents(){

  }

  public ListEvents(ArrayList<Event> listEvent) {
    mListEvent = listEvent;
  }

  public ArrayList<Event> getListEvent() {

    return mListEvent;
  }

  public void setListEvent(ArrayList<Event> listEvent) {
    mListEvent = listEvent;
  }




}
