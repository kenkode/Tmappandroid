package com.upstridge.tmapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class VehicleActivity extends Activity {

    String url = "http://192.168.56.1/tmapp/android/vehicle.php";
    SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        Bundle bundle = getIntent().getExtras();
        String province = bundle.getString("province");
        String destination = bundle.getString("destination");
        String origin = bundle.getString("origin");

        searchBar = (SearchView)findViewById(R.id.searchBar);

        //Toast.makeText(vehicles.this, province + " Province"+","+destination, Toast.LENGTH_SHORT).show();

        final ListView lv = (ListView) findViewById(R.id.vehicleList);
        final VehicleData v = new VehicleData(this, url, lv, province, destination, origin, searchBar);

        v.execute();
    }

}
