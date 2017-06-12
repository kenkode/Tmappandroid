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

        final EditText adults = (EditText) findViewById(R.id.numadults);
        final EditText child = (EditText) findViewById(R.id.numadults);
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
                if(adults.getText().toString().trim().equals("") || child.getText().toString().trim().equals("")){
                    Toast.makeText(NumberOfClients.this,"Please insert atleast one person",Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(getApplicationContext(), eventBooking.class);
                    Bundle b = new Bundle();
                    b.putString("eventid", eventid);
                    b.putInt("adults", Integer.parseInt(adults.getText().toString()));
                    b.putInt("child", Integer.parseInt(child.getText().toString()));
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
