package com.upstridge.tmapp.hotel;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SearchView;

import com.upstridge.tmapp.R;

public class HotelActivity extends Activity {

    String url = "http://10.0.2.2/tmapp/android/searchHotel.php";
    //String url = "http://admin.upstridge.co.ke/android/searchHotel.php";

    SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        Bundle bundle = getIntent().getExtras();
        String date = bundle.getString("date");
        String time = bundle.getString("time");
        String area = bundle.getString("area");

        searchBar = (SearchView)findViewById(R.id.searchBar);

        //Toast.makeText(VehicleActivity.this, time, Toast.LENGTH_SHORT).show();

        final ListView lv = (ListView) findViewById(R.id.hotelList);
        final HotelData v = new HotelData(this, url, lv, date, time, area, searchBar);
        v.execute();
    }

}
