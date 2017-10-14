package com.upstridge.tmapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 10/10/17.
 */

public class Hire {
    @Expose
    private String sdate;
    @Expose
    private String stime;
    @Expose
    private String edate;
    @Expose
    private String etime;
    @Expose
    private String location;
    @Expose
    private String organization;
    @Expose
    private String firstname;
    @Expose
    private String lastname;
    @Expose
    private String phone;
    @Expose
    private String email;
    @Expose
    private String idno;
    @Expose
    private String mode;
    @Expose
    private String[] types;
    @Expose
    private String[] nums;
    @Expose
    private String[] amounts;
    @Expose
    private long diffDays;
    @Expose
    private double amount;

    @SerializedName("success")
    @Expose
    private String success;

    public Hire(String sdate, String stime, String edate, String etime, String location, String organization, String firstname, String lastname, String phone, String email, String idno, String mode, String[] types, String[] nums, String[] amounts, long diffDays, double amount) {
        this.sdate = sdate;
        this.stime = stime;
        this.edate = edate;
        this.etime = etime;
        this.location = location;
        this.organization = organization;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.idno = idno;
        this.mode = mode;
        this.types = types;
        this.nums = nums;
        this.amounts = amounts;
        this.diffDays = diffDays;
        this.amount = amount;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String[] getNums() {
        return nums;
    }

    public void setNums(String[] nums) {
        this.nums = nums;
    }

    public String[] getAmounts() {
        return amounts;
    }

    public void setAmounts(String[] amounts) {
        this.amounts = amounts;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getDiffDays() {
        return diffDays;
    }

    public void setDiffDays(long diffDays) {
        this.diffDays = diffDays;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
