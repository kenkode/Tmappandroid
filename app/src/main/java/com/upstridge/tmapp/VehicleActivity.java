package com.upstridge.tmapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class VehicleActivity extends Activity {

    String url = "http://192.168.56.1/tmapp/android/searchVehicle.php";
    SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        Bundle bundle = getIntent().getExtras();
        String date = bundle.getString("date");
        String time = bundle.getString("time");
        String destination = bundle.getString("to");
        String origin = bundle.getString("from");
        String organization = bundle.getString("organization");

        searchBar = (SearchView)findViewById(R.id.searchBar);

        //Toast.makeText(vehicles.this, province + " Province"+","+destination, Toast.LENGTH_SHORT).show();

        final ListView lv = (ListView) findViewById(R.id.vehicleList);
        final VehicleData v = new VehicleData(this, url, lv, date, time, destination, origin, searchBar);

        v.execute();
    }

}
