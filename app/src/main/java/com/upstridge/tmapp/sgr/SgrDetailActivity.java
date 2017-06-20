package com.upstridge.tmapp.sgr;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SearchView;

import com.upstridge.tmapp.R;

public class SgrDetailActivity extends Activity {

    String url = "http://192.168.2.101/tmapp/android/searchTrain.php";
    //String url = "http://admin.upstridge.co.ke/android/searchVehicle.php";

    SearchView searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sgr_detail);

        Bundle bundle = getIntent().getExtras();
        String date = bundle.getString("date");
        String time = bundle.getString("time");
        String destination = bundle.getString("to");
        String origin = bundle.getString("from");
        String organization = bundle.getString("organization");

        searchBar = (SearchView)findViewById(R.id.searchBar);

        //Toast.makeText(VehicleActivity.this, time, Toast.LENGTH_SHORT).show();

        final ListView lv = (ListView) findViewById(R.id.trainList);
        final SgrData v = new SgrData(this, url, lv,date, time, destination, origin, searchBar);

        v.execute();
    }

}
