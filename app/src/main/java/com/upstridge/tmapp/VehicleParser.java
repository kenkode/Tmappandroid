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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Wango on 12/11/2016.
 */
public class VehicleParser  extends AsyncTask<Void, Integer, Integer> {

    Context c;
    ListView lv;
    String data;
    String province;
    String destination;
    String origin;
    SearchView searchView;

    ArrayList<Vehicles> veh = new ArrayList<>();

    CustomVehicleAdapter adapter;

            ProgressDialog pd;

    public VehicleParser(Context c, String data, ListView lv, String province, String destination, String origin, SearchView searchView) {
        this.c = c;
        this.data = data;
        this.lv = lv;
        this.province = province;
        this.destination = destination;
        this.origin = origin;
        this.searchView = searchView;
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
            adapter = new CustomVehicleAdapter(c,veh);
            /*final ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,veh){

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view =super.getView(position, convertView, parent);

                    TextView textView=(TextView) view.findViewById(android.R.id.text1);

                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(20);

                    return view;
                }
            };*/
            lv.setAdapter(adapter);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    int textlength = newText.length();
                    ArrayList<Vehicles> tempArrayList = new ArrayList<Vehicles>();
                    for(Vehicles c: veh){
                        if (textlength <= c.getName().length()) {
                            if (c.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }
                    adapter = new CustomVehicleAdapter(c, tempArrayList);
                    lv.setAdapter(adapter);
                    //adapter.getFilter().filter(newText);
                    return false;
                }
            });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(c, TravellersActivity.class);
                    Bundle b = new Bundle();
                    b.putString("destination", destination);
                    b.putString("province", province);
                    b.putString("vehicle", veh.get(position).toString());
                    b.putString("origin",origin);
                    i.putExtras(b);
                    c.startActivity(i);
                }
            });

        }else{
            Toast.makeText(c,"Data not available",Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }


    private int parse() {
        try {

            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            veh.clear();

            Vehicles vehicle;

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                int id = jo.getInt("id");
                String name = jo.getString("name");
                String imageUrl = "http://192.168.56.1/tmapp/public/uploads/logo/"+jo.getString("logo");

                vehicle = new Vehicles();
                vehicle.setId(id);
                vehicle.setName(name);
                vehicle.setImageUrl(imageUrl);

                veh.add(vehicle);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
