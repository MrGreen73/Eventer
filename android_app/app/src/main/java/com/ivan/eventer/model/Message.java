package com.ivan.eventer.model;

/**
 * Created by user on 27.04.2018.
 */

public class Message {

    private Long date;
    private String from;
    private String message;
    
    public Message() {
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
