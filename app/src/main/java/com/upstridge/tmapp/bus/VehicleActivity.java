package com.upstridge.tmapp.bus;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.data.VehicleData;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

public class VehicleActivity extends Activity {

    String url = BASE_URL + "android/searchVehicle.php";
    //String url = "http://admin.upstridge.co.ke/android/searchVehicle.php";

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

        //Toast.makeText(VehicleActivity.this, time, Toast.LENGTH_SHORT).show();

        final RecyclerView lv = (RecyclerView) findViewById(R.id.vehicleList);
        final GridLayoutManager llm = new GridLayoutManager(this, 1);

        lv.setLayoutManager(llm);
        //final VehicleData v = new VehicleData(this, url, lv,date, time, destination, origin, searchBar);
        final LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorLayout.setVisibility(View.GONE);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.load_cars);

        final VehicleData v = new VehicleData(this,date, time, destination, origin);
        v.getVehicles(date, time, destination, origin, lv, errorLayout, progressBar,searchBar);

        //v.execute();
    }

}
