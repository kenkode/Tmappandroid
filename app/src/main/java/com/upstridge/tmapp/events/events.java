package com.upstridge.tmapp.events;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SearchView;

import com.upstridge.tmapp.R;

public class events extends Activity {

    String url = "http://10.0.2.2:81/tmapp/android/searchEvent.php";
    //String url = "http://admin.upstridge.co.ke/android/searchEvent.php";

    SearchView searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        searchBar = (SearchView)findViewById(R.id.searchBar);

        //Toast.makeText(VehicleActivity.this, time, Toast.LENGTH_SHORT).show();

        final ListView lv = (ListView) findViewById(R.id.eventList);
        final eventDownloader v = new eventDownloader(this, url, lv, searchBar);

        v.execute();
    }

}
