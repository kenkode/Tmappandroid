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

    String time_txt = "";
    //String url = "http://admin.upstridge.co.ke/android/destination.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_time);

        String[] t=new String[]{"Morning","Afternoon","Evening","Night"};
        final Spinner time = (Spinner) findViewById(R.id.time);
        Button search = (Button) findViewById(R.id.customers);

        ArrayAdapter<String> timeArray= new ArrayAdapter<String>(CheckTimeActivity.this,android.R.layout.simple_spinner_item, t);
        timeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time.setAdapter(timeArray);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_txt = time.getSelectedItem().toString();
                Intent i = new Intent(CheckTimeActivity.this, HotelActivity.class);
                Bundle b = new Bundle();
                b.putString("time", time_txt);
                i.putExtras(b);
                CheckTimeActivity.this.startActivity(i);
            }
        });
    }

}
