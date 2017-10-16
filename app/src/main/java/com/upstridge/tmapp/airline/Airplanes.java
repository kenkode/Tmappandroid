package com.upstridge.tmapp.airline;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.data.PlaneData;

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

        final RecyclerView lv = (RecyclerView) findViewById(R.id.vehicleList);
        final GridLayoutManager llm = new GridLayoutManager(this, 1);

        lv.setLayoutManager(llm);
        //final VehicleData v = new VehicleData(this, url, lv,date, time, destination, origin, searchBar);
        final LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorLayout.setVisibility(View.GONE);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.load_cars);

        final PlaneData p = new PlaneData(this,date, time, destination, origin);
        p.getAirplanes(date, time, destination, origin, lv, errorLayout, progressBar,searchBar);

        /*final ListView lv = (ListView) findViewById(R.id.vehicleList);
        final AirlineData v = new AirlineData(this, url, lv,date, time, destination, origin, searchBar);

        v.execute();*/
    }


}
