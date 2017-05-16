package com.upstridge.tmapp;

import android.widget.Spinner;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Wango-PC on 5/12/2017.
 */

public class DataPackage {
    String firstname,lastname,email,phone,idno,fare,paymentmode,organization,vehicle,destination,origin,date,time,arrival,departure;

    public DataPackage(String firstname, String lastname, String email, String phone, String idno,String fare, String paymentmode, String organization, String vehicle, String destination, String origin, String date, String time, String arrival, String departure) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.idno = idno;
        this.fare = fare;
        this.paymentmode = paymentmode;
        this.organization = organization;
        this.vehicle = vehicle;
        this.destination = destination;
        this.origin = origin;
        this.date = date;
        this.time = time;
        this.arrival = arrival;
        this.departure = departure;
    }

    public String packData(){
        JSONObject jo = new JSONObject();
        StringBuffer sb = new StringBuffer();

        try{
            jo.put("firstname", firstname);
            jo.put("lastname", lastname);
            jo.put("email", email);
            jo.put("phone", phone);
            jo.put("idno", idno);
            jo.put("amount", fare);
            jo.put("paymentmode", paymentmode);
            jo.put("organization", organization);
            jo.put("vehicle", vehicle);
            jo.put("destination", destination);
            jo.put("origin", origin);
            jo.put("arrival", arrival);
            jo.put("departure", departure);
            jo.put("date", date);
            jo.put("time", time);

            Boolean firstValue = true;

            Iterator it = jo.keys();

            do{
                String key   = it.next().toString();
                String value = jo.get(key).toString();

                if(firstValue){
                    firstValue = false;
                }else{
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key,"UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(value,"UTF-8"));
            }while (it.hasNext());

            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}