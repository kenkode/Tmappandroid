package com.upstridge.tmapp.sgr;

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
import com.upstridge.tmapp.data.TrainData;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

public class SgrDetailActivity extends Activity {

    String url = BASE_URL + "android/searchTrain.php";
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

        final RecyclerView lv = (RecyclerView) findViewById(R.id.trainList);
        final GridLayoutManager llm = new GridLayoutManager(this, 1);

        lv.setLayoutManager(llm);
        //final VehicleData v = new VehicleData(this, url, lv,date, time, destination, origin, searchBar);
        final LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorLayout.setVisibility(View.GONE);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.load_cars);

        final TrainData v = new TrainData(this,date, time, destination, origin);
        v.getTrains(date, time, destination, origin, lv, errorLayout, progressBar,searchBar);
        /*final SgrData v = new SgrData(this, url, lv,date, time, destination, origin, searchBar);

        v.execute();*/
    }

}
