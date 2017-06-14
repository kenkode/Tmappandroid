package com.upstridge.tmapp.airline;

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

/**
 * Created by Wango on 12/11/2016.
 */
public class AirlineParser  extends AsyncTask<Void, Integer, Integer> {

    Context c;
    ListView lv;
    String data;
    //String date;
    String time;
    String destination;
    String origin;
    String type;
    String capacity;
    String vipprice;
    String businessprice;
    String ecprice;
    String childprice;
    String arr;
    String dep;
    SearchView searchView;

    ArrayList<Aeroplanes> veh = new ArrayList<>();

    CustomAeroplaneAdapter adapter;

    ProgressDialog pd;

    public AirlineParser(Context c, String data, ListView lv, String time, String destination, String origin, SearchView searchView) {
        this.c = c;
        this.data = data;
        this.lv = lv;
        //this.date = date;
        this.time = time;
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
            adapter = new CustomAeroplaneAdapter(c,veh);
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
                    ArrayList<Aeroplanes> tempArrayList = new ArrayList<Aeroplanes>();
                    for(Aeroplanes c: veh){
                        if (textlength <= c.getName().length()) {
                            if (c.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }
                    adapter = new CustomAeroplaneAdapter(c, tempArrayList);
                    lv.setAdapter(adapter);
                    //adapter.getFilter().filter(newText);
                    return false;
                }
            });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String vehicle =((TextView)view.findViewById(R.id.vehicleName)).getText().toString();
                    String arrival =((TextView)view.findViewById(R.id.location)).getText().toString();
                    String departure =((TextView)view.findViewById(R.id.contact)).getText().toString();
                    String vip =((TextView)view.findViewById(R.id.vipfare)).getText().toString();
                    String economic =((TextView)view.findViewById(R.id.economicfare)).getText().toString();
                    String organization =((TextView)view.findViewById(R.id.organization)).getText().toString();
                    String vid =((TextView)view.findViewById(R.id.vehicleid)).getText().toString();
                    String firstclassapply =((TextView)view.findViewById(R.id.firstclassapply)).getText().toString();

                    Intent i = new Intent(c, SeatSelectionActivityNew.class);
                    Bundle b = new Bundle();
                    b.putString("destination", destination);
                    //b.putString("date", date);
                    b.putString("time", time);
                    b.putString("vehicle", vehicle);
                    b.putString("origin",origin);
                    b.putString("arrival", arr);
                    b.putString("departure", dep);
                    b.putString("vip", vipprice);
                    b.putString("business", businessprice);
                    b.putString("economic", ecprice);
                    b.putString("children", childprice);
                    b.putString("type",type);
                    b.putString("capacity",capacity);
                    b.putString("organization", organization);
                    b.putString("vid", vid);
                    b.putString("firstclassapply", firstclassapply);
                    i.putExtras(b);
                     c.startActivity(i);
                }
            });

        }else{
            Toast.makeText(c,"No aeroplanes available",Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }


    private int parse() {
        try {

            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            veh.clear();

            Aeroplanes aeroplane;

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                String name = jo.getString("name");
                String imageUrl = "http://192.168.2.101/tmapp/public/uploads/logo/"+jo.getString("logo");
                String route = jo.getString("oname") +" to "+ jo.getString("dname");
                String arrival = "Arrival : "+jo.getString("arrival");
                String departure = "Departure : "+jo.getString("departure");
                arr = jo.getString("arrival");
                dep = jo.getString("departure");
                type = jo.getString("type");
                capacity = jo.getString("capacity");
                String vipfare = "Vip Fare : KES "+jo.getString("firstclass");
                String businessfare = "Business Fare : KES "+jo.getString("business");
                String economicfare = "Economic Fare : KES "+jo.getString("economic");
                String childrenfare = "Children Fare(%) : "+jo.getString("children");
                vipprice = jo.getString("firstclass");
                businessprice = jo.getString("business");
                ecprice = jo.getString("economic");
                childprice = jo.getString("children");
                String organization = jo.getString("organization_id");
                String vehicleid = jo.getString("vehicle_id");
                String firsclassapply = jo.getString("firstclass_apply");

                //String price = "VIP : KES "+jo.getString("firstclass") + " Economic : KES "+jo.getString("economic");

                aeroplane = new Aeroplanes();
                aeroplane.setName(name);
                aeroplane.setImageUrl(imageUrl);
                aeroplane.setRoute(route);
                aeroplane.setArrival(arrival);
                aeroplane.setDeparture(departure);
                aeroplane.setVipprice(vipfare);
                aeroplane.setBusinessfare(businessfare);
                aeroplane.setEconomicfare(economicfare);
                aeroplane.setChildrenfare(childrenfare);
                aeroplane.setOrganization(organization);
                aeroplane.setVehicleid(vehicleid);
                aeroplane.setFirstclassapply(firsclassapply);
                veh.add(aeroplane);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
