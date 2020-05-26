package com.example.urbanutils;

import java.io.Serializable;

public class Provider implements Serializable {
    private String name,address,businessType,email,phoneNumber,upiID,rating;
    private double lattitude,longitude;
    private boolean Approval ;
    private int noOfCustomer;

    public int getNoOfCustomer() {
        return noOfCustomer;
    }

    public void setNoOfCustomer(int noOfCustomer) {
        this.noOfCustomer = noOfCustomer;
    }

    public Provider() {
    }


    public String getUpiID() {
        return upiID;
    }

    public void setUpiID(String upiID) {
        this.upiID = upiID;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Provider(String name, String address, String businessType, String email, double lattitude, double longitude, String phoneNumber, boolean approval, String rating, String upiID,int noOfCustomer) {
        this.name = name;
        this.address = address;
        this.phoneNumber=phoneNumber;
        this.businessType = businessType;
        this.email = email;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.Approval=approval;
        this.rating=rating;
        this.upiID=upiID;
        this.noOfCustomer=noOfCustomer;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isApproval() {
        return Approval;
    }

    public void setApproval(boolean approval) {
        Approval = approval;
    }



}
