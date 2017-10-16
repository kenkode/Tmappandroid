package com.upstridge.tmapp.taxi;

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
import com.upstridge.tmapp.data.TaxiData;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

public class TaxiActivity extends Activity {

    String url = BASE_URL + "android/searchTaxi.php";
    SearchView searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi);

        searchBar = (SearchView)findViewById(R.id.searchTaxi);
        final RecyclerView lv = (RecyclerView) findViewById(R.id.taxi);
        final GridLayoutManager llm = new GridLayoutManager(this, 1);

        lv.setLayoutManager(llm);
        //final VehicleData v = new VehicleData(this, url, lv,date, time, destination, origin, searchBar);
        final LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorLayout.setVisibility(View.GONE);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.load_cars);

        final TaxiData v = new TaxiData(this);
        v.getTaxis(lv, errorLayout, progressBar,searchBar);
        /*final TaxiDownloader d = new TaxiDownloader(this, url, lv, searchBar);

        d.execute();*/
    }

}
