package com.upstridge.tmapp;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Wango-PC on 5/12/2017.
 */

public class Connector {
    public static HttpURLConnection connect(String urlAddress) {
        try{
            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setConnectTimeout(20000);
            con.setReadTimeout(20000);
            con.setDoInput(true);
            con.setDoOutput(true);

            return con;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
