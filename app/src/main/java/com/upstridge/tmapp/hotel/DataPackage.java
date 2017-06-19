package com.upstridge.tmapp.hotel;

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

    String paymentmode,organization,hotelid,price,branchid,type,time,date;

    int slots,adults,child;

    public DataPackage(ArrayList<String> firstname, ArrayList<String> lastname, ArrayList<String> email, ArrayList<String> phone, ArrayList<String> idno,String price, String paymentmode, String organization, String hotelid, int slots, String type, String branchid, String date, String time, int adults, int child) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.idno = idno;
        this.price = price;
        this.paymentmode = paymentmode;
        this.slots = slots;
        this.organization = organization;
        this.hotelid = hotelid;
        this.type = type;
        this.branchid = branchid;
        this.date = date;
        this.time = time;
        this.adults = adults;
        this.child = child;

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
            jo.put("amount", price);
            jo.put("paymentmode", paymentmode);
            jo.put("slots", slots);
            jo.put("organization", organization);
            jo.put("hotel", hotelid);
            jo.put("type", type);
            jo.put("branchid", branchid);
            jo.put("date", date);
            jo.put("time", time);
            jo.put("adults", adults);
            jo.put("child", child);


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
