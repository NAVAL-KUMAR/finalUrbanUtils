package com.example.urbanutils;

import java.io.Serializable;

public class MapLatLonClass implements Serializable {
    private double cusLat,cusLong,proLat,proLong;
    private String providerPhone,customerPhone;

    public MapLatLonClass() {
    }

    public MapLatLonClass(double cusLat, double cusLong, double proLat, double proLong,String providerPhone,String customerPhone) {
        this.cusLat = cusLat;
        this.cusLong = cusLong;
        this.proLat = proLat;
        this.proLong = proLong;
        this.providerPhone=providerPhone;
        this.customerPhone=customerPhone;
    }

    public double getCusLat() {
        return cusLat;
    }

    public void setCusLat(double cusLat) {
        this.cusLat = cusLat;
    }

    public double getCusLong() {
        return cusLong;
    }

    public void setCusLong(double cusLong) {
        this.cusLong = cusLong;
    }

    public double getProLat() {
        return proLat;
    }

    public void setProLat(double proLat) {
        this.proLat = proLat;
    }

    public double getProLong() {
        return proLong;
    }

    public void setProLong(double proLong) {
        this.proLong = proLong;
    }

    public String getProviderPhone() {
        return providerPhone;
    }

    public void setProviderPhone(String providerPhone) {
        this.providerPhone = providerPhone;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
