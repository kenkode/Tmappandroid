package com.upstridge.tmapp.hotel;

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

public class BranchParser extends AsyncTask<Void,Integer,Integer> {

    Context c;
    String data;
    Spinner area;
    Button search;
    String date_txt;
    String time_txt;
    String area_txt;
    String arrival;
    String departure;
    String hotel;
    EditText btnpick;
    EditText timepick;
    ArrayList<String> branches = new ArrayList<>();
    ProgressDialog pd;

    public BranchParser(Context c, String data, EditText btnpick, EditText timepick, Spinner area, Button search){
        this.c = c;
        this.data = data;
        this.btnpick = btnpick;
        this.timepick = timepick;
        this.area = area;
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
            ArrayAdapter adapter = new ArrayAdapter(c, android.R.layout.simple_list_item_1, branches);
            // apply the Adapter:
            area.setAdapter(adapter);

            area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(c,origins.get(position),Toast.LENGTH_SHORT).show();
                    area_txt = branches.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    date_txt = btnpick.getText().toString();
                    time_txt = timepick.getText().toString();
                    Intent i = new Intent(c, HotelActivity.class);
                    Bundle b = new Bundle();
                    b.putString("area", area_txt);
                    b.putString("date", date_txt);
                    b.putString("time", time_txt);
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

            branches.clear();

            for(int i = 0; i<ja.length(); i++){
                jo = ja.getJSONObject(i);
                String name = jo.getString("name");
                branches.add(name);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
