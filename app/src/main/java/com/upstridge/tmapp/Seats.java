package com.upstridge.tmapp;

import java.io.Serializable;

/**
 * Created by Wango-PC on 5/16/2017.
 */

public class Seats implements Serializable{
    private String number;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNumber() {

        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Seats(String number, String photo) {
        this.number = number;
        this.photo = photo;
    }
}
