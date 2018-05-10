package com.ivan.eventer.model;

import java.util.ArrayList;

/**
 * Created by user on 10.05.2018.
 */
public class MessageArray {
    private ArrayList<Message> messages = new ArrayList<>();

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
