package com.upstridge.tmapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RequestTaxiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_taxi);

        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        Button select   = (Button)findViewById(R.id.button3);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String request = ((RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                //Toast.makeText(RequestTaxiActivity.this,request,Toast.LENGTH_SHORT).show();
            }
        });

    }

}
