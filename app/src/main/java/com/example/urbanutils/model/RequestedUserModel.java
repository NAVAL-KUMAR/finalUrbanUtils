package com.example.urbanutils.model;

public class RequestedUserModel {
    private String phoneNumber;
    public RequestedUserModel(){}

    public RequestedUserModel(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
