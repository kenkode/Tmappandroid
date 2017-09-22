package com.upstridge.tmapp.sgr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.upstridge.tmapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wango-PC on 6/8/2017.
 */

public class SeatsParser  extends AsyncTask<Void, Integer, Integer> {

    Context c;
    String data;

    List<String> seats = new ArrayList<String>();

    ProgressDialog pd;

    public SeatsParser(Context c, String data) {
        this.c = c;
        this.data = data;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Parser");
        pd.setMessage("Parsing...Please wait");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        //Toast.makeText(c,integer,Toast.LENGTH_SHORT).show();

        if(integer == 1){
            StringBuilder builder = new StringBuilder();
            for (String seat : seats) {
                builder.append(seat + ",");
            }
            Toast.makeText(c,builder,Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(c,"No seats available",Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }


    private int parse() {
        try {

            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                int total = jo.getInt("total");

                for (int j=1;j<=total;j++) {

                    seats.add(jo.getString("seatno" + (j)));
                }

            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}

