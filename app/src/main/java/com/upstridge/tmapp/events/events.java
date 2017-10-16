package com.upstridge.tmapp.events;

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
import com.upstridge.tmapp.data.EventData;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

public class events extends Activity {

    String url = BASE_URL + "android/searchEvent.php";
    //String url = "http://admin.upstridge.co.ke/android/searchEvent.php";

    SearchView searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        searchBar = (SearchView)findViewById(R.id.searchBar);

        //Toast.makeText(VehicleActivity.this, time, Toast.LENGTH_SHORT).show();

        final RecyclerView lv = (RecyclerView) findViewById(R.id.eventList);
        final GridLayoutManager llm = new GridLayoutManager(this, 1);

        lv.setLayoutManager(llm);
        //final VehicleData v = new VehicleData(this, url, lv,date, time, destination, origin, searchBar);
        final LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorLayout.setVisibility(View.GONE);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.load_cars);

        final EventData e = new EventData(this);
        e.getEvents(lv, errorLayout, progressBar,searchBar);
        /*final eventDownloader v = new eventDownloader(this, url, lv, searchBar);

        v.execute();*/
    }

}
