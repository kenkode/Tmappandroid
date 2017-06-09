package com.upstridge.tmapp.events;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.upstridge.tmapp.R;

public class NumberOfClients extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_of_clients);

        final EditText numcust = (EditText) findViewById(R.id.numcust);
        Button customers = (Button) findViewById(R.id.customers);

        Bundle bundle = getIntent().getExtras();
        final String organization = bundle.getString("organization");
        final String eventid = bundle.getString("eventid");
        final String vip = bundle.getString("vip");
        final String economic = bundle.getString("economic");
        final String children = bundle.getString("children");


        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numcust.getText().toString().trim().equals("")){
                    numcust.setError("Please insert number of customers your booking for!");
                }else {
                    Intent i = new Intent(getApplicationContext(), eventBooking.class);
                    Bundle b = new Bundle();
                    b.putString("eventid", eventid);
                    b.putInt("slots", Integer.parseInt(numcust.getText().toString()));
                    b.putString("vip", vip);
                    b.putString("economic", economic);
                    b.putString("children", children);
                    b.putString("organization", organization);
                    i.putExtras(b);
                    startActivity(i);
                }
            }
        });
    }

}
