package com.upstridge.tmapp.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.upstridge.tmapp.R;

public class Customers extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        final EditText adults = (EditText) findViewById(R.id.numadults);
        final EditText child = (EditText) findViewById(R.id.numchildren);
        Button customers = (Button) findViewById(R.id.customers);

        Bundle bundle = getIntent().getExtras();
        final String organization = bundle.getString("organization");
        final String roomid = bundle.getString("roomid");
        final String price = bundle.getString("price");
        final String type = bundle.getString("type");
        final String adultno = bundle.getString("adults");
        final String date = bundle.getString("date");
        final String time = bundle.getString("time");
        final String branchid = bundle.getString("branchid");
        final String children = bundle.getString("children");


        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adults.getText().toString().trim().equals("") && child.getText().toString().trim().equals("")){
                    Toast.makeText(Customers.this,"Please insert atleast one person",Toast.LENGTH_SHORT).show();
                }else if(!adults.getText().toString().equals("") && Integer.parseInt(adults.getText().toString()) > Integer.parseInt(adultno)){
                    Toast.makeText(Customers.this,"Maximum adults number is "+adultno,Toast.LENGTH_SHORT).show();
                }else if(!child.getText().toString().equals("") && Integer.parseInt(child.getText().toString()) > Integer.parseInt(children)){
                    Toast.makeText(Customers.this,"Maximum children number is "+children,Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(Customers.this,price,Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), hotelBooking.class);
                    Bundle b = new Bundle();
                    b.putString("roomid", roomid);
                    if(!adults.getText().toString().equals("")) {
                        b.putInt("adults", Integer.parseInt(adults.getText().toString()));
                    }else{
                        b.putInt("adults", 0);
                    }
                    if(!child.getText().toString().equals("")) {
                        b.putInt("child", Integer.parseInt(child.getText().toString()));
                    }else{
                        b.putInt("child", 0);
                    }
                    b.putString("price", price);
                    b.putString("type", type);
                    b.putString("adultno", adultno);
                    b.putString("branchid", branchid);
                    b.putString("children", children);
                    b.putString("time", time);
                    b.putString("date", date);
                    b.putString("organization", organization);
                    i.putExtras(b);
                    startActivity(i);
                }
            }
        });
    }

}
