package com.upstridge.tmapp;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SelectPeriod extends Activity {

    String url = "http://10.0.2.2/tmapp/android/destination.php";
    //String url = "http://admin.upstridge.co.ke/android/destination.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_period);

        String[] t=new String[]{"Morning","Afternoon","Evening","Night"};
        Spinner time = (Spinner) findViewById(R.id.time);
        Spinner from = (Spinner) findViewById(R.id.from);
        Spinner to = (Spinner) findViewById(R.id.to);
        Button search = (Button) findViewById(R.id.button);

        ArrayAdapter<String> timeArray= new ArrayAdapter<String>(SelectPeriod.this,android.R.layout.simple_spinner_item, t);
        timeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(timeArray);

        final PeriodDownloader d = new PeriodDownloader(this, url, time, from, to, search);
        d.execute();
    }

}
