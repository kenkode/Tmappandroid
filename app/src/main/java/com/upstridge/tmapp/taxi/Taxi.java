package com.upstridge.tmapp.taxi;

/**
 * Created by Wango-PC on 5/6/2017.
 */

public class Taxi {
    String name;
    String imageUrl;
    String ecprice;
    String capacity;
    String Vehicleid;
    String organization;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEcprice() {
        return ecprice;
    }

    public void setEcprice(String ecprice) {
        this.ecprice = ecprice;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getVehicleid() {
        return Vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        Vehicleid = vehicleid;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
