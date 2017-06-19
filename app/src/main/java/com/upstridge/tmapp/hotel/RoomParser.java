package com.upstridge.tmapp.hotel;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.upstridge.tmapp.R;


import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Wango on 12/11/2016.
 */
public class RoomParser extends AsyncTask<Void, Integer, Integer> {

    Context c;
    ListView lv;
    String data;
    String date;
    String time;
    String type;
    String price;
    String capacity;
    String adults;
    String children;
    String branchid;
    String roomid;
    String organization;
    SearchView searchView;

    ArrayList<Room> rm = new ArrayList<>();

    CustomRoomAdapter adapter;

    ProgressDialog pd;

    public RoomParser(Context c, String data, ListView lv, String date, String time, String branchid, SearchView searchView) {
        this.c = c;
        this.data = data;
        this.lv = lv;
        this.branchid = branchid;
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
            adapter = new CustomRoomAdapter(c,rm);
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
                    ArrayList<Room> tempArrayList = new ArrayList<Room>();
                    for(Room c: rm){
                        if (textlength <= c.getName().length()) {
                            if (c.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }
                    adapter = new CustomRoomAdapter(c, tempArrayList);
                    lv.setAdapter(adapter);
                    //adapter.getFilter().filter(newText);
                    return false;
                }
            });

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String roomtype = ((TextView) view.findViewById(R.id.roomName)).getText().toString();

                        Intent i = new Intent(c, Customers.class);
                        Bundle b = new Bundle();
                        b.putString("branch", branchid);
                        b.putString("date", date);
                        b.putString("time", time);
                        b.putString("type", type);
                        b.putString("organization", organization);
                        b.putString("adults", adults);
                        b.putString("children", children);
                        b.putString("capacity", capacity);
                        b.putString("price", price);
                        b.putString("roomid", roomid);
                        b.putString("roomtype", roomtype);
                        i.putExtras(b);
                        c.startActivity(i);

                }
            });

        }else{
            Toast.makeText(c,"No rooms available",Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }


    private int parse() {
        try {

            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            rm.clear();

            Room room;

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);

                String hotelprice = "";
                if(day == Calendar.SUNDAY){
                    if(jo.getString("sun").equals("null")){
                        hotelprice = "Price : Undefined";
                        price = jo.getString("sun");
                    }else {
                        hotelprice = "Price : KES " + jo.getString("sun");
                        price = jo.getString("sun");
                    }
                }else if(day == Calendar.MONDAY){
                    if(jo.getString("mon").equals("null")){
                        hotelprice = "Price : Undefined";
                        price = jo.getString("mon");
                    }else {
                        hotelprice = "Price : KES " + jo.getString("mon");
                        price = jo.getString("mon");
                    }
                }else if(day == Calendar.TUESDAY){
                    if(jo.getString("tue").equals("null")){
                        hotelprice = "Price : Undefined";
                        price = jo.getString("tue");
                    }else {
                        hotelprice = "Price : KES " + jo.getString("tue");
                        price = jo.getString("tue");
                    }
                }else if(day == Calendar.WEDNESDAY){
                    if(jo.getString("wen").equals("null")){
                        hotelprice = "Price : Undefined";
                        price = jo.getString("wen");
                    }else {
                        hotelprice = "Price : KES " + jo.getString("wen");
                        price = jo.getString("wen");
                    }
                }else if(day == Calendar.THURSDAY){
                    if(jo.getString("thur").equals("null")){
                        hotelprice = "Price : Undefined";
                        price = jo.getString("thur");
                    }else {
                        hotelprice = "Price : KES " + jo.getString("thur");
                        price = jo.getString("thur");
                    }
                }else if(day == Calendar.FRIDAY){
                    if(jo.getString("fri").equals("null")){
                        hotelprice = "Price : Undefined";
                        price = jo.getString("fri");
                    }else {
                        hotelprice = "Price : KES " + jo.getString("fri");
                        price = jo.getString("fri");
                    }
                }else if(day == Calendar.SATURDAY){
                    if(jo.getString("sat").equals("null")){
                        hotelprice = "Price : Undefined";
                        price = jo.getString("sat");
                    }else {
                        hotelprice = "Price : KES " + jo.getString("sat");
                        price = jo.getString("sat");
                    }
                }

                String name = jo.getString("name");
                String imageUrl = "http://10.0.2.2/tmapp/public/uploads/hotel/rooms/"+jo.getString("image");
                String availability = "Rooms available : "+jo.getString("room_count");
                String adultno = "Number of adults : "+jo.getString("adults");
                String childno = "Number of children : "+jo.getString("children");
                type = jo.getString("name");
                roomid = jo.getString("id");
                capacity = jo.getString("room_count");


                adults = jo.getString("adults");
                children = jo.getString("children");
                organization = jo.getString("organization_id");
                //String price = "VIP : KES "+jo.getString("firstclass") + " Economic : KES "+jo.getString("economic");

                room = new Room();
                room.setName(name);
                room.setImageUrl(imageUrl);
                room.setPrice(hotelprice);
                room.setAdults(adultno);
                room.setChildren(childno);
                room.setAvailability(availability);
                rm.add(room);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
