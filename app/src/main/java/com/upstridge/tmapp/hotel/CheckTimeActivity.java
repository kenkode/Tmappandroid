package com.upstridge.tmapp.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.upstridge.tmapp.R;

public class CheckTimeActivity extends Activity {

    String url = "http://10.0.2.2/tmapp/android/branches.php";
    //String url = "http://admin.upstridge.co.ke/android/branches.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_time);

        String[] t=new String[]{"Morning","Afternoon","Evening","Night"};
        final Spinner time = (Spinner) findViewById(R.id.time);
        final Spinner area = (Spinner) findViewById(R.id.area);
        Button search = (Button) findViewById(R.id.customers);

        ArrayAdapter<String> timeArray= new ArrayAdapter<String>(CheckTimeActivity.this,android.R.layout.simple_spinner_item, t);
        timeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(timeArray);

        final BranchDownloader d = new BranchDownloader(this, url, time, area, search);
        d.execute();
    }

}
