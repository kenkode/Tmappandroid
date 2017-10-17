package com.upstridge.tmapp.hotel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.upstridge.tmapp.R;
import com.upstridge.tmapp.adapters.CustomHotelAdapter;
import com.upstridge.tmapp.models.Hotels;


import java.util.ArrayList;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

/**
 * Created by Wango on 12/11/2016.
 */
public class HotelParser extends AsyncTask<Void, Integer, Integer> {

    Context c;
    RecyclerView lv;
    String data;
    String area;
    String date;
    String time;
    String type;
    String capacity;
    String adults;
    String children;
    String branch;
    String branchid;
    String hotelid;
    SearchView searchView;

    ArrayList<Hotels> hot = new ArrayList<>();

    CustomHotelAdapter adapter;

    ProgressDialog pd;

    public HotelParser(Context c, String data, RecyclerView lv, String date, String time, String area, SearchView searchView) {
        this.c = c;
        this.data = data;
        this.lv = lv;
        this.area = area;
        this.time = time;
        this.date = date;
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
            adapter = new CustomHotelAdapter(c,hot,date,time,area);
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
                    ArrayList<Hotels> tempArrayList = new ArrayList<Hotels>();
                    for(Hotels c: hot){
                        if (textlength <= c.getName().length()) {
                            if (c.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }
                    adapter = new CustomHotelAdapter(c, tempArrayList, date, time, area);
                    lv.setAdapter(adapter);
                    //adapter.getFilter().filter(newText);
                    return false;
                }
            });

            /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String hotel =((TextView)view.findViewById(R.id.hotelName)).getText().toString();
                    String organization =((TextView)view.findViewById(R.id.organization)).getText().toString();

                    Intent i = new Intent(c, rooms.class);
                    Bundle b = new Bundle();
                    b.putString("area", area);
                    b.putString("date", date);
                    b.putString("time", time);
                    b.putString("hotel", hotel);
                    b.putString("organization", organization);
                    b.putString("branch", branch);
                    b.putString("branchid", branchid);
                    i.putExtras(b);
                    c.startActivity(i);
                }
            });*/

        }else{
            Toast.makeText(c,"No hotels available",Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }


    private int parse() {
        try {

            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            hot.clear();

            Hotels hotel;

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                String name = jo.getString("name");
                String imageUrl = BASE_URL + "public/uploads/logo/"+jo.getString("logo");
                String hotelbranch = "Branch : "+jo.getString("branch");
                branch = jo.getString("branch");
                branchid = jo.getString("id");
                //String hotelprice = "Price : KES "+jo.getString("price");
                //price = jo.getString("price");
                String organization = jo.getString("organization_id");

                //String price = "VIP : KES "+jo.getString("firstclass") + " Economic : KES "+jo.getString("economic");

                hotel = new Hotels();
                hotel.setName(name);
                hotel.setImageUrl(imageUrl);
                hotel.setBranch(hotelbranch);
                hotel.setOrganization(organization);
                hot.add(hotel);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
