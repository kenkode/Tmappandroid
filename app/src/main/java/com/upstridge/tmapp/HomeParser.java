package com.upstridge.tmapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.SearchView.OnQueryTextListener;

import java.util.ArrayList;

/**
 * Created by Wango-PC on 4/29/2017.
 */

public class HomeParser extends AsyncTask<Void,Integer,Integer> {

    Context c;
    Spinner from;
    String data;
    Spinner to;
    Button search;
    String frm_txt;
    String to_txt;
    String arrival;
    String departure;
    String vehicle;
    EditText btnpick;
    EditText timepick;
    ArrayList<String> origins = new ArrayList<>();
    ProgressDialog pd;

    public HomeParser(Context c, String data, Spinner from, Spinner to, Button search, EditText btnpick, EditText timepick){
        this.c = c;
        this.data = data;
        this.from = from;
        this.to = to;
        this.search = search;
        this.btnpick = btnpick;
        this.timepick = timepick;
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
                    Intent i = new Intent(c, VehicleActivity.class);
                    Bundle b = new Bundle();
                    b.putString("from", frm_txt);
                    b.putString("to", to_txt);
                    b.putString("arrival", arrival);
                    b.putString("departure", departure);
                    b.putString("vehicle", vehicle);
                    b.putString("date", btnpick.getText().toString());
                    b.putString("time", timepick.getText().toString());
                    i.putExtras(b);
                    c.startActivity(i);
                }
            });

        }else{
            Toast.makeText(c,"Unable to parse data...Please try again later",Toast.LENGTH_SHORT).show();
        }
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
