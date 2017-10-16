package com.upstridge.tmapp.taxi;

/**
 * Created by Wango-PC on 5/4/2017.
 */

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
import com.upstridge.tmapp.adapters.CustomTaxiAdapter;
import com.upstridge.tmapp.models.Taxi;

import com.upstridge.tmapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

public class TaxiParser extends AsyncTask<Void, Integer, Integer> {

    Context c;
    RecyclerView lv;
    String data;
    SearchView searchView;
    String organization;
    String capacity;
    String ecprice;
    String vehicleid;
    ArrayList<Taxi> taxi = new ArrayList<>();

    CustomTaxiAdapter adapter;

    ProgressDialog pd;

    public TaxiParser(Context c, String data, RecyclerView lv, SearchView searchView) {
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
            adapter = new CustomTaxiAdapter(c,taxi);
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
                    ArrayList<Taxi> tempArrayList = new ArrayList<Taxi>();
                    for(Taxi c: taxi){
                        if (textlength <= c.getName().length()) {
                            if (c.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }
                    adapter = new CustomTaxiAdapter(c, tempArrayList);
                    lv.setAdapter(adapter);
                    //adapter.getFilter().filter(newText);
                    return false;
                }
            });

            /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String vehicle =((TextView)view.findViewById(R.id.hotelName)).getText().toString();
                    String organization =((TextView)view.findViewById(R.id.organization)).getText().toString();
                    ecprice =((TextView)view.findViewById(R.id.ecprice)).getText().toString();
                    capacity =((TextView)view.findViewById(R.id.capacity)).getText().toString();
                    vehicleid =((TextView)view.findViewById(R.id.vehicleid)).getText().toString();

                    //Toast.makeText(c,ecprice+"-"+vehicleid+"-"+capacity,Toast.LENGTH_LONG).show();

                    Intent i = new Intent(c, PeriodActivity.class);
                    Bundle b = new Bundle();
                    b.putString("vehicle", vehicle);
                    b.putString("economic", ecprice);
                    b.putString("capacity",capacity);
                    b.putString("organization", organization);
                    b.putString("vid", vehicleid);
                    i.putExtras(b);
                    c.startActivity(i);
                }
            });*/
        }else{
            Toast.makeText(c,"No Taxi available",Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }


    private int parse() {
        try {

            JSONArray ja = new JSONArray(data);
            JSONObject jo = null;

            taxi.clear();

            Taxi tx;

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);

                String name = jo.getString("name");
                String imageUrl = BASE_URL + "public/uploads/logo/"+jo.getString("logo");
                vehicleid = jo.getString("id");
                ecprice = jo.getString("economic");
                capacity = jo.getString("capacity");
                organization = jo.getString("organization_id");

                //String price = "VIP : KES "+jo.getString("firstclass") + " Economic : KES "+jo.getString("economic");

                tx = new Taxi();
                tx.setName(name);
                tx.setImageUrl(imageUrl);
                //tx.setEcprice(ecprice);
                tx.setCapacity(capacity);
                tx.setVehicleid(vehicleid);
                taxi.add(tx);
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
