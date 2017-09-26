package com.upstridge.tmapp.bus;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SearchView;

import com.upstridge.tmapp.R;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

public class DestinationActivity extends Activity {
    String url = BASE_URL + "android/origin.php";
    SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        Bundle bundle = getIntent().getExtras();
        String province = bundle.getString("province");
        String origin = bundle.getString("origin");
        //Toast.makeText(destinations.this, province+"Province", Toast.LENGTH_SHORT).show();

        searchBar = (SearchView)findViewById(R.id.searchBar);
        final ListView lv = (ListView) findViewById(R.id.destinationList);
        final DestinationDownloader d = new DestinationDownloader(this, url, lv, province,origin, searchBar);

        d.execute();
    }

}

