package com.upstridge.tmapp;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Wango-PC on 5/6/2017.
 */

public class PicassoClient {
    public static void downloadImage(Context c, String imageUrl, String type, ImageView img){
        if(imageUrl.length() > 0 && imageUrl != null){
            if(type == "bus"){
                Picasso.with(c).load(imageUrl).placeholder(R.drawable.default_bus).into(img);
            }else if(type == "taxi") {
                Picasso.with(c).load(imageUrl).placeholder(R.drawable.default_taxi).into(img);
            }else if(type == "train") {
                Picasso.with(c).load(imageUrl).placeholder(R.drawable.default_train).into(img);
            }else if(type == "event") {
                Picasso.with(c).load(imageUrl).placeholder(R.drawable.events_calendar).into(img);
            }else if(type == "aeroplane") {
                Picasso.with(c).load(imageUrl).placeholder(R.drawable.aeroplane).into(img);
            }
        }else {
            if(type == "bus"){
                Picasso.with(c).load(R.drawable.default_bus).into(img);
            }else if(type == "taxi") {
                Picasso.with(c).load(R.drawable.default_taxi).into(img);
            }else if(type == "train") {
                Picasso.with(c).load(R.drawable.default_train).into(img);
            }else if(type == "event") {
                Picasso.with(c).load(R.drawable.events_calendar).into(img);
            }else if(type == "aeroplane") {
                Picasso.with(c).load(R.drawable.aeroplane).into(img);
            }
        }
    }
}
