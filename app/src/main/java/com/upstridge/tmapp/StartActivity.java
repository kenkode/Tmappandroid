package com.upstridge.tmapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.upstridge.tmapp.airline.AirlineActivity;
import com.upstridge.tmapp.airline.PlaneHomeActivity;
import com.upstridge.tmapp.events.events;
import com.upstridge.tmapp.hotel.CheckTimeActivity;
import com.upstridge.tmapp.sgr.SgrActivity1;
import com.upstridge.tmapp.sgr.SgrHomeActivity;

public class StartActivity extends AppCompatActivity {

    ViewFlipper travel,airplane,hotels,taxi,sgr,events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int bus[]={R.drawable.coastern,R.drawable.oxygen,R.drawable.north_rift,
                R.drawable.services_oxygen
        };

        int plane[]={R.drawable.kq,R.drawable.kq1,R.drawable.kq3,
                R.drawable.kq4,R.drawable.passengers
        };

        int rooms[]={R.drawable.hilton1,R.drawable.hilton2,R.drawable.hilton4,R.drawable.room1,R.drawable.room2,R.drawable.serena4,
                R.drawable.hilton
        };

        int taxis[]={R.drawable.uber2,R.drawable.uber,R.drawable.taxi1,R.drawable.taxi2
        };

        int trains[]={R.drawable.sgr,R.drawable.sgr1
        };

        int evts[]={R.drawable.concert1,R.drawable.livemusic,R.drawable.action,R.drawable.churchil,R.drawable.churchil2,R.drawable.aud
        };

        travel = (ViewFlipper) findViewById(R.id.travel);

            /** Start Flipping */
            travel.startFlipping();

        for(int i=0;i<bus.length;i++)
        {
            //  This will create dynamic image view and add them to ViewFlipper
            setTravelFlipperImage(bus[i]);


        }

        airplane = (ViewFlipper) findViewById(R.id.airplane);

        /** Start Flipping */
        airplane.startFlipping();

        for(int i=0;i<plane.length;i++)
        {
            //  This will create dynamic image view and add them to ViewFlipper
            setAirplaneFlipperImage(plane[i]);


        }

        hotels = (ViewFlipper) findViewById(R.id.hotel);

        /** Start Flipping */
        hotels.startFlipping();

        for(int i=0;i<rooms.length;i++)
        {
            //  This will create dynamic image view and add them to ViewFlipper
            setHotelFlipperImage(rooms[i]);


        }

        taxi = (ViewFlipper) findViewById(R.id.taxi);

        /** Start Flipping */
        taxi.startFlipping();

        for(int i=0;i<taxis.length;i++)
        {
            //  This will create dynamic image view and add them to ViewFlipper
            setTaxiFlipperImage(taxis[i]);


        }

        sgr = (ViewFlipper) findViewById(R.id.train);

        /** Start Flipping */
        sgr.startFlipping();

        for(int i=0;i<trains.length;i++)
        {
            //  This will create dynamic image view and add them to ViewFlipper
            setSgrFlipperImage(trains[i]);


        }

        events = (ViewFlipper) findViewById(R.id.event);

        /** Start Flipping */
        events.startFlipping();

        for(int i=0;i<evts.length;i++)
        {
            //  This will create dynamic image view and add them to ViewFlipper
            setEventFlipperImage(evts[i]);


        }

    }

    private void setTravelFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        ImageButton image = new ImageButton(getApplicationContext());
        image.setBackgroundResource(res);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class).putExtra("mode", "travel"));
            }
        });
        travel.addView(image);
    }

    private void setAirplaneFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        ImageButton image = new ImageButton(getApplicationContext());
        image.setBackgroundResource(res);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PlaneHomeActivity.class).putExtra("mode", "airline"));
            }
        });
        airplane.addView(image);
    }

    private void setHotelFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        ImageButton image = new ImageButton(getApplicationContext());
        image.setBackgroundResource(res);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CheckTimeActivity.class).putExtra("mode", "hotel"));
            }
        });
        hotels.addView(image);
    }

    private void setTaxiFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        ImageButton image = new ImageButton(getApplicationContext());
        image.setBackgroundResource(res);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), com.upstridge.tmapp.taxi.TaxiActivity.class).putExtra("mode", "taxi"));
            }
        });
        taxi.addView(image);
    }

    private void setSgrFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        ImageButton image = new ImageButton(getApplicationContext());
        image.setBackgroundResource(res);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SgrHomeActivity.class).putExtra("mode", "train"));
            }
        });
        sgr.addView(image);
    }

    private void setEventFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        ImageButton image = new ImageButton(getApplicationContext());
        image.setBackgroundResource(res);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), events.class).putExtra("mode", "events"));
            }
        });
        events.addView(image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "Refunds selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }

}
