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
import android.widget.SearchView.OnQueryTextListener;

import java.util.ArrayList;

/**
 * Created by Wango-PC on 4/29/2017.
 */

public class Parser extends AsyncTask<Void,Integer,Integer> {

    Context c;
    ListView lv;
    String data;
    String province;
    ArrayList<String> origins = new ArrayList<>();
    ProgressDialog pd;
    SearchView searchView;

    public Parser(Context c, String data, ListView lv, String province, SearchView searchView){
        this.c = c;
        this.data = data;
        this.lv = lv;
        this.province = province;
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

        if(integer == 1){
            final ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1, origins){

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

            searchView.setOnQueryTextListener(new OnQueryTextListener() {
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
                    Intent i = new Intent(c, DestinationActivity.class);
                    Bundle b = new Bundle();
                    b.putString("origin", origins.get(position));
                    b.putString("province", province);
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
