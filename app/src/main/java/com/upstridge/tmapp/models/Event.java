package com.upstridge.tmapp.models;

/**
 * Created by Wango-PC on 6/8/2017.
 */

public class Event {
    String name;
    String imageUrl;
    String vipprice;
    String economic;
    String children;
    String description;
    String address;
    String contact;
    String eventid;
    String slots;
    String organizationId;
    String date;


    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

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

    public String getVipprice() {
        return vipprice;
    }

    public void setVipprice(String vipprice) {
        this.vipprice = vipprice;
    }

    public String getEconomic() {
        return economic;
    }

    public void setEconomic(String economic) {
        this.economic = economic;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getDescription() {
        return description;

    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getEventid() {
        return eventid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}