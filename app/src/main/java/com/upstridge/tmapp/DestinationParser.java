package com.upstridge.tmapp;

/**
 * Created by Wango-PC on 5/4/2017.
 */

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

public class DestinationParser extends AsyncTask<Void, Integer, Integer> {

    Context c;
    ListView lv;
    String data;
    String province;
    String origin;
    SearchView searchView;
    ArrayList<String> des = new ArrayList<>();

    ProgressDialog pd;

    public DestinationParser(Context c, String data, ListView lv, String province, String origin, SearchView searchView) {
        this.c = c;
        this.data = data;
        this.lv = lv;
        this.province = province;
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
            final ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1, des){

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view =super.getView(position, convertView, parent);

                            TextView textView=(TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(20);

                            return view;
                        }
                    };
            lv.setAdapter(adapter);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Snackbar.make(view, des.get(position), Snackbar.LENGTH_SHORT).show();
                    Intent i = new Intent(c, VehicleActivity.class);
                    Bundle b = new Bundle();
                    b.putString("destination", des.get(position));
                    b.putString("province", province);
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

            des.clear();

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                String name = jo.getString("name");

                des.add(name);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
