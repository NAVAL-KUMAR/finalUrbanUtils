package com.example.urbanutils.model;

public class ChatModel {
    private String message,phone;

    public ChatModel() {
    }

    public ChatModel(String message, String phone) {
        this.message = message;
        this.phone = phone;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
