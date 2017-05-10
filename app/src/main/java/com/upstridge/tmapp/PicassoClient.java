package com.upstridge.tmapp;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Wango-PC on 5/6/2017.
 */

public class PicassoClient {
    public static void downloadImage(Context c, String imageUrl, ImageView img){
        if(imageUrl.length() > 0 && imageUrl != null){
            Picasso.with(c).load(imageUrl).placeholder(R.drawable.bus3).into(img);
        }else {
            Picasso.with(c).load(R.drawable.bus3).into(img);
        }
    }
}
