package com.upstridge.tmapp.airline;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SearchView;

import com.upstridge.tmapp.R;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

public class Airplanes extends Activity {

    String url = BASE_URL + "android/searchAeroplane.php";
    //String url = "http://admin.upstridge.co.ke/android/searchAeroplane.php";

    SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airplanes);

        Bundle bundle = getIntent().getExtras();
        String date = bundle.getString("date");
        String time = bundle.getString("time");
        String destination = bundle.getString("to");
        String origin = bundle.getString("from");
        String organization = bundle.getString("organization");

        searchBar = (SearchView)findViewById(R.id.searchBar);

        //Toast.makeText(VehicleActivity.this, time, Toast.LENGTH_SHORT).show();

        final ListView lv = (ListView) findViewById(R.id.vehicleList);
        final AirlineData v = new AirlineData(this, url, lv,date, time, destination, origin, searchBar);

        v.execute();
    }

}
