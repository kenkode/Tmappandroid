package com.upstridge.tmapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        int bus[]={R.drawable.coastern,R.drawable.oxygen,R.drawable.north_rift,
                R.drawable.services_oxygen
        };

        int plane[]={R.drawable.planeimg,R.drawable.planeimg2,R.drawable.airplane_pic
        };

        int rooms[]={R.drawable.room1,R.drawable.room2,R.drawable.room4
        };

        int taxis[]={R.drawable.carimg,R.drawable.taximg,R.drawable.taxi1,R.drawable.taxi2
                ,R.drawable.taxi_uber
        };

        int trains[]={R.drawable.trainimg,R.drawable.sgr1
        };

        int evts[]={R.drawable.concert1,R.drawable.livemusic,R.drawable.action,R.drawable.eventimg,R.drawable.eventimg2
                ,R.drawable.eventimg3
        };

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                width/2,
                200);

       /* airplane = new ViewFlipper(this);
        airplane.setId(R.id.airplane);
        layoutParams.setMargins(20,0,0,0);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.textView);
        airplane.setLayoutParams(layoutParams);
        rl.addView(airplane, layoutParams);

        travel = new ViewFlipper(this);
        travel.setId(R.id.travel);
        layoutParams.setMargins(20,0,0,0);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.textView);
        layoutParams.addRule(RelativeLayout.RIGHT_OF,airplane.getId());
        travel.setLayoutParams(layoutParams);
        rl.addView(travel, layoutParams);*/

            /** Start Flipping */
        travel = (ViewFlipper) findViewById(R.id.travel);
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

        RelativeLayout relativeLayout = new RelativeLayout(this);
        ImageButton image = new ImageButton(getApplicationContext());
        image.setBackgroundResource(res);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class).putExtra("mode", "travel"));
            }
        });
        relativeLayout.addView(image);

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                70);



        TextView tv = new TextView(StartActivity.this);
        tv.setText("Bus");
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(18);
        tv.setTypeface(Typeface.SANS_SERIF);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setMaxLines(2);
        tv.setBackgroundColor(Color.argb(99, 0, 0, 0));
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        tv.setLayoutParams(param);
        relativeLayout.addView(tv, param);
        travel.addView(relativeLayout);
    }

    private void setAirplaneFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        RelativeLayout relativeLayout = new RelativeLayout(this);

        ImageButton image = new ImageButton(getApplicationContext());
        image.setBackgroundResource(res);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PlaneHomeActivity.class).putExtra("mode", "airline"));
            }
        });
        relativeLayout.addView(image);

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                70);

        TextView tv = new TextView(StartActivity.this);
        tv.setText("Airplane");
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(18);
        tv.setTypeface(Typeface.SANS_SERIF);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setMaxLines(2);
        tv.setBackgroundColor(Color.argb(99, 0, 0, 0));
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        tv.setLayoutParams(param);
        relativeLayout.addView(tv, param);
        airplane.addView(relativeLayout);
    }

    private void setHotelFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        RelativeLayout relativeLayout = new RelativeLayout(this);

        ImageButton image = new ImageButton(getApplicationContext());
        image.setBackgroundResource(res);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CheckTimeActivity.class).putExtra("mode", "hotel"));
            }
        });
        relativeLayout.addView(image);

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                70);

        TextView tv = new TextView(StartActivity.this);
        tv.setText("Hotel");
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(18);
        tv.setTypeface(Typeface.SANS_SERIF);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setMaxLines(2);
        tv.setBackgroundColor(Color.argb(99, 0, 0, 0));
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        tv.setLayoutParams(param);
        relativeLayout.addView(tv, param);
        hotels.addView(relativeLayout);
    }

    private void setTaxiFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        RelativeLayout relativeLayout = new RelativeLayout(this);

        ImageButton image = new ImageButton(getApplicationContext());
        image.setBackgroundResource(res);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), com.upstridge.tmapp.taxi.TaxiActivity.class).putExtra("mode", "taxi"));
            }
        });
        relativeLayout.addView(image);

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                70);

        TextView tv = new TextView(StartActivity.this);
        tv.setText("Taxi");
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(18);
        tv.setTypeface(Typeface.SANS_SERIF);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setMaxLines(2);
        tv.setBackgroundColor(Color.argb(99, 0, 0, 0));
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        tv.setLayoutParams(param);
        relativeLayout.addView(tv, param);
        taxi.addView(relativeLayout);
    }

    private void setSgrFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        RelativeLayout relativeLayout = new RelativeLayout(this);

        ImageButton image = new ImageButton(getApplicationContext());
        image.setBackgroundResource(res);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SgrHomeActivity.class).putExtra("mode", "train"));
            }
        });
        relativeLayout.addView(image);

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                70);

        TextView tv = new TextView(StartActivity.this);
        tv.setText("SGR");
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(18);
        tv.setTypeface(Typeface.SANS_SERIF);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setMaxLines(2);
        tv.setBackgroundColor(Color.argb(99, 0, 0, 0));
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        tv.setLayoutParams(param);
        relativeLayout.addView(tv, param);
        sgr.addView(relativeLayout);
    }

    private void setEventFlipperImage(int res) {
        Log.i("Set Filpper Called", res+"");
        RelativeLayout relativeLayout = new RelativeLayout(this);

        ImageButton image = new ImageButton(getApplicationContext());
        image.setBackgroundResource(res);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), events.class).putExtra("mode", "events"));
            }
        });
        relativeLayout.addView(image);

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                70);

        TextView tv = new TextView(StartActivity.this);
        tv.setText("Events");
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(18);
        tv.setTypeface(Typeface.SANS_SERIF);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setMaxLines(2);
        tv.setBackgroundColor(Color.argb(99, 0, 0, 0));
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        tv.setLayoutParams(param);
        relativeLayout.addView(tv, param);
        events.addView(relativeLayout);
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
