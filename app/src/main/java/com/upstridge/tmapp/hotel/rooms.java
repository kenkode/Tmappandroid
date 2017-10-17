package com.upstridge.tmapp.hotel;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.data.RData;
import com.upstridge.tmapp.hotel.RoomData;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

public class rooms extends Activity {

    String url = BASE_URL + "android/searchRoom.php";
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

        //Toast.makeText(rooms.this, branchid, Toast.LENGTH_SHORT).show();

        final RecyclerView lv = (RecyclerView) findViewById(R.id.room);
        final GridLayoutManager llm = new GridLayoutManager(this, 1);

        lv.setLayoutManager(llm);
        //final VehicleData v = new VehicleData(this, url, lv,date, time, destination, origin, searchBar);
        final LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorLayout.setVisibility(View.GONE);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.load_cars);

        final RData v = new RData(this, date, time, branchid);
        v.getRooms(date, time, branchid, lv, errorLayout, progressBar,searchBar);
        /*final RoomData v = new RoomData(this, url, lv, date, time, branchid, searchBar);
        v.execute();*/
    }

}
