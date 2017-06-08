package com.upstridge.tmapp.sgr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Wango-PC on 6/8/2017.
 */

public class SgrParser extends AsyncTask<Void,Integer,Integer> {

    Context c;
    Spinner time;
    Spinner from;
    String data;
    Spinner to;
    Button search;
    String time_txt;
    String frm_txt;
    String to_txt;
    String arrival;
    String departure;
    String train;
    ArrayList<String> origins = new ArrayList<>();
    ProgressDialog pd;

    public SgrParser(Context c, String data, Spinner time, Spinner from, Spinner to, Button search){
        this.c = c;
        this.data = data;
        this.time = time;
        this.from = from;
        this.to = to;
        this.search = search;
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

        if(integer == 1){
            ArrayAdapter adapter = new ArrayAdapter(c, android.R.layout.simple_list_item_1, origins);
            // apply the Adapter:
            from.setAdapter(adapter);
            to.setAdapter(adapter);

            from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(c,origins.get(position),Toast.LENGTH_SHORT).show();
                    frm_txt = origins.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(c,origins.get(position),Toast.LENGTH_SHORT).show();
                    to_txt = origins.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    time_txt = time.getSelectedItem().toString();
                    Intent i = new Intent(c, SgrDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putString("from", frm_txt);
                    b.putString("to", to_txt);
                    b.putString("time", time_txt);
                    b.putString("arrival", arrival);
                    b.putString("departure", departure);
                    b.putString("train", train);
                    i.putExtras(b);
                    c.startActivity(i);
                }
            });

        }/*else{
            Toast.makeText(c,"Unable to parse data...Please try again later",Toast.LENGTH_SHORT).show();
        }*/
        pd.dismiss();
    }

    private int parse(){
        try{
            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            origins.clear();

            for(int i = 0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);
                String name = jo.getString("name");
                origins.add(name);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}