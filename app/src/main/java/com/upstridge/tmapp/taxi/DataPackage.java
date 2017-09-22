package com.upstridge.tmapp.taxi;

import android.widget.Spinner;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import static com.upstridge.tmapp.R.id.seats;

/**
 * Created by Wango-PC on 5/12/2017.
 */

public class DataPackage {
    ArrayList<String> firstname,lastname,email,phone,idno;

    String paymentmode,organization,vehicle,destination,origin,economic;

    int capacity;
    double amount;

    public DataPackage(ArrayList<String> firstname, ArrayList<String> lastname, ArrayList<String> email, ArrayList<String> phone, ArrayList<String> idno,double amount, String paymentmode, String organization, String vehicle, String destination, String origin, int capacity,String economic) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.idno = idno;
        this.amount = amount;
        this.paymentmode = paymentmode;
        this.capacity = capacity;
        this.organization = organization;
        this.vehicle = vehicle;
        this.destination = destination;
        this.origin = origin;
        this.economic = economic;
        //this.date = date;
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
            jo.put("amount", amount);
            jo.put("paymentmode", paymentmode);
            jo.put("capacity", capacity);
            jo.put("organization", organization);
            jo.put("vehicle", vehicle);
            jo.put("destination", destination);
            jo.put("origin", origin);
            jo.put("economic", economic);

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
