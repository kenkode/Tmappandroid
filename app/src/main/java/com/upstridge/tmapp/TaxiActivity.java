package com.upstridge.tmapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SearchView;

public class TaxiActivity extends Activity {

    String url = "http://192.168.56.1/tmapp/android/searchTaxi.php";
    SearchView searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi);

        searchBar = (SearchView)findViewById(R.id.searchTaxi);
        final ListView lv = (ListView) findViewById(R.id.taxi);
        final TaxiDownloader d = new TaxiDownloader(this, url, lv, searchBar);

        d.execute();
    }

}
