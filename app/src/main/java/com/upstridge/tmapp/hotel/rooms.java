package com.upstridge.tmapp.hotel;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SearchView;

import com.upstridge.tmapp.R;

public class rooms extends Activity {

    String url = "http://10.0.2.2/tmapp/android/searchRoom.php";
    //String url = "http://admin.upstridge.co.ke/android/searchHotel.php";

    SearchView searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        Bundle bundle = getIntent().getExtras();
        final String organization = bundle.getString("organization");
        final String area = bundle.getString("area");
        final String date = bundle.getString("date");
        final String time = bundle.getString("time");
        final String branchid = bundle.getString("branchid");

        searchBar = (SearchView)findViewById(R.id.searchRoom);

        //Toast.makeText(VehicleActivity.this, time, Toast.LENGTH_SHORT).show();

        final ListView lv = (ListView) findViewById(R.id.room);
        final RoomData v = new RoomData(this, url, lv, date, time, branchid, searchBar);
        v.execute();
    }

}
