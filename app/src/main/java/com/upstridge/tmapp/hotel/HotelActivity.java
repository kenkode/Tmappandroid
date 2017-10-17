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

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.data.HData;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

public class HotelActivity extends Activity {

    String url = BASE_URL + "android/searchHotel.php";
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

        final RecyclerView lv = (RecyclerView) findViewById(R.id.hotelList);
        final GridLayoutManager llm = new GridLayoutManager(this, 1);

        lv.setLayoutManager(llm);
        //final VehicleData v = new VehicleData(this, url, lv,date, time, destination, origin, searchBar);
        final LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorLayout.setVisibility(View.GONE);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.load_cars);

        final HData v = new HData(this, date, time, area);
        v.getHotels(date, time, area, lv, errorLayout, progressBar,searchBar);

        /*final ListView lv = (ListView) findViewById(R.id.hotelList);
        final HotelData v = new HotelData(this, url, lv, date, time, area, searchBar);
        v.execute();*/
    }

}
