package com.upstridge.tmapp.events;

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
 * Created by Wango-PC on 6/8/2017.
 */

public class eventParser  extends AsyncTask<Void, Integer, Integer> {

    Context c;
    ListView lv;
    String data;
    //String date;
    String date;
    String slots;
    String vipprice;
    String ecprice;
    String children;
    SearchView searchView;

    ArrayList<Event> ev = new ArrayList<>();

    CustomEventAdapter adapter;

    ProgressDialog pd;

    public eventParser(Context c, String data, ListView lv, SearchView searchView) {
        this.c = c;
        this.data = data;
        this.lv = lv;
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
            adapter = new CustomEventAdapter(c,ev);
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
                    ArrayList<Event> tempArrayList = new ArrayList<Event>();
                    for(Event c: ev){
                        if (textlength <= c.getName().length()) {
                            if (c.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }
                    adapter = new CustomEventAdapter(c, tempArrayList);
                    lv.setAdapter(adapter);
                    //adapter.getFilter().filter(newText);
                    return false;
                }
            });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String organization =((TextView)view.findViewById(R.id.organization)).getText().toString();
                    String eventid =((TextView)view.findViewById(R.id.eventid)).getText().toString();

                    Intent i = new Intent(c, NumberOfClients.class);
                    Bundle b = new Bundle();
                    b.putString("vip", vipprice);
                    b.putString("economic", ecprice);
                    b.putString("children",children);
                    b.putString("slots",slots);
                    b.putString("organization", organization);
                    b.putString("eventid", eventid);
                    i.putExtras(b);
                    c.startActivity(i);
                }
            });

        }else{
            Toast.makeText(c,"No events available",Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }


    private int parse() {
        try {

            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            ev.clear();

            Event event;

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                String name = jo.getString("name");
                String imageUrl = "http://192.168.2.101/tmapp/public/uploads/logo/"+jo.getString("image");
                String remaininglots = "Remaining Slots : "+jo.getString("slots");
                String description = "Description : "+jo.getString("description");
                String contact = "Contact : "+jo.getString("contact");
                String address = "Address : "+jo.getString("address");
                String dt = "Date : "+jo.getString("date");
                date = jo.getString("date");
                String vip = "Vip Entrance : KES "+jo.getString("vip");
                String normal = "Normal Entrance : KES "+jo.getString("normal");
                String child = "Children Entrance : KES "+jo.getString("children");
                vipprice = jo.getString("vip");
                slots = jo.getString("slots");
                ecprice = jo.getString("normal");
                children = jo.getString("children");
                String organization = jo.getString("organization_id");
                String Evid = jo.getString("id");

                //String price = "VIP : KES "+jo.getString("firstclass") + " Economic : KES "+jo.getString("economic");

                event = new Event();
                event.setName(name);
                event.setImageUrl(imageUrl);
                event.setDescription(description);
                event.setSlots(remaininglots);
                event.setContact(contact);
                event.setAddress(address);
                event.setDate(dt);
                event.setVipprice(vip);
                event.setEconomic(normal);
                event.setChildren(child);
                event.setOrganizationId(organization);
                event.setEventid(Evid);
                ev.add(event);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}

