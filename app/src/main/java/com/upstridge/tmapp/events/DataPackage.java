package com.upstridge.tmapp.events;

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
    ArrayList<String> firstname,lastname,email,phone,idno,fare;

    String paymentmode,organization,eventid,vip,economic,children;

    int slots,adults,child;

    public DataPackage(ArrayList<String> firstname, ArrayList<String> lastname, ArrayList<String> email, ArrayList<String> phone, ArrayList<String> idno,ArrayList<String> fare, String paymentmode, String organization, String eventid, int slots, String vip, String economic, String children, int adults, int child) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.idno = idno;
        this.fare = fare;
        this.paymentmode = paymentmode;
        this.slots = slots;
        this.organization = organization;
        this.eventid = eventid;
        this.vip = vip;
        this.economic = economic;
        this.children = children;
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
            jo.put("amount", fare);
            jo.put("paymentmode", paymentmode);
            jo.put("slots", slots);
            jo.put("organization", organization);
            jo.put("event", eventid);
            jo.put("vip", vip);
            jo.put("economic", economic);
            jo.put("children", children);
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
