package com.upstridge.tmapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.upstridge.tmapp.airline.AirlineActivity;
import com.upstridge.tmapp.airline.PlaneHomeActivity;
import com.upstridge.tmapp.events.events;
import com.upstridge.tmapp.hotel.CheckTimeActivity;
import com.upstridge.tmapp.sgr.SgrActivity1;
import com.upstridge.tmapp.sgr.SgrHomeActivity;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageButton airplane = (ImageButton)findViewById(R.id.airplane);
        ImageButton travel = (ImageButton)findViewById(R.id.travel);
        ImageButton train = (ImageButton)findViewById(R.id.train);
        ImageButton taxi = (ImageButton)findViewById(R.id.taxi);
        ImageButton hotel = (ImageButton)findViewById(R.id.hotel);
        ImageButton event = (ImageButton)findViewById(R.id.event);

        airplane.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PlaneHomeActivity.class).putExtra("mode", "airline"));
            }
        });

        travel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class).putExtra("mode", "travel"));
            }
        });

        train.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SgrHomeActivity.class).putExtra("mode", "train"));
            }
        });

        taxi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), com.upstridge.tmapp.taxi.TaxiActivity.class).putExtra("mode", "taxi"));
            }
        });

        hotel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CheckTimeActivity.class).putExtra("mode", "hotel"));
            }
        });

        event.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), events.class).putExtra("mode", "events"));
            }
        });
    }

}
