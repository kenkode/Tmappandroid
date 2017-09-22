package com.upstridge.tmapp.hotel;

/**
 * Created by Wango-PC on 6/16/2017.
 */

public class Room {
    String name;
    String imageUrl;
    String price;
    String availability;
    String adults;
    String children;
    String organization;
    String roomid;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String hotelid) {
        this.roomid = hotelid;
    }
}
