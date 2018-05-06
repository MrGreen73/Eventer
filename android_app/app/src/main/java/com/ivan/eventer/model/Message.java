package com.ivan.eventer.model;

import java.util.Date;

public class Message {

    String textMessage; // Текст сообщения
    String authorMessage; // Автор сообщения
    long timeMessage; // Время сообщения

    // Конструктор сообщения
    public Message(String textMessage, String authorMessage) {

        this.textMessage = textMessage;
        this.authorMessage = authorMessage;
        timeMessage = new Date().getTime();

    }


    // Пустой конструктор
    public Message() { }

    // Геттеры и сеттеры
    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getAuthorMessage() {
        return authorMessage;
    }

    public void setAuthorMessage(String authorMessage) {
        this.authorMessage = authorMessage;
    }

    public long getTimeMessage() {
        return timeMessage;
    }

    public void setTimeMessage(long timeMessage) {
        this.timeMessage = timeMessage;
    }
}