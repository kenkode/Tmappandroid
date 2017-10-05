package com.upstridge.tmapp.carhire;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.adapters.CustomCarHireAdapter;
import com.upstridge.tmapp.bus.HideHintEditText;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CarHireBooking extends AppCompatActivity {

    Spinner mode;
    EditText firstname,lastname,email,phone,idno;
    TextView amount;
    TextView [] cartype,id,carprice;
    Button book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_hire_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] modes=new String[]{"Mpesa","Airtel Money","VISA"};

        mode=(Spinner) findViewById(R.id.mode);
        ArrayAdapter<String> modeArray= new ArrayAdapter<String>(CarHireBooking.this,android.R.layout.simple_spinner_item, modes);
        modeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mode.setAdapter(modeArray);

        Bundle bundle = getIntent().getExtras();
        final String sdate = bundle.getString("startdate");
        final String stime = bundle.getString("starttime");
        final String edate = bundle.getString("enddate");
        final String etime = bundle.getString("endtime");
        final String location = bundle.getString("location");
        final int count = bundle.getInt("count");
        for(int i = 0; i < count; i++){
            final String car_i = bundle.getString("car"+i);
            final String num_i = bundle.getString("number"+i);
            final String carid_i = bundle.getString("carid"+i);
        }

        firstname = (EditText)findViewById(R.id.firstname);
        lastname = (EditText)findViewById(R.id.lastname);
        email = (EditText)findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.phone);
        idno = (EditText)findViewById(R.id.idno);

        cartype = new TextView[count];
        carprice = new TextView[count];
        id = new TextView[count];
        //cartype = new TextView[CustomCarHireAdapter.cars.size()];
        //seaterror = new TextView[CustomCarHireAdapter.cars.size()];

        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.lin);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(280,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 30);

        TextView head = new TextView(this);
        head.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        head.setTextSize(20);
        head.setTypeface(Typeface.create("sans-serif-smallcaps",Typeface.BOLD));
        head.setText("Cars to hire");
        head.setLayoutParams(params);
        rootLayout.addView(head);

        double total = 0;

        for (int i = 0; i < count; i++){

            total += (bundle.getDouble("price"+i) * Integer.parseInt(bundle.getString("number"+i)));

            DecimalFormat formatter = new DecimalFormat("#,##0.00");

                LinearLayout temp = new LinearLayout(this);
                temp.setOrientation(LinearLayout.HORIZONTAL);
                temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,3));


                /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(280,
                        LinearLayout.LayoutParams.WRAP_CONTENT);*/
                params.setMargins(0, 0, 0, 30);
                //relativeLayout[i] = new RelativeLayout(this);
                //relativeLayout[i].setId(R.id.dynamic+(i+1));
                // Defining the RelativeLayout layout parameters.
                // In this case I want to fill its parent

                // Creating a new TextView

                TextView tv = new TextView(this);
                tv.setLayoutParams(new LinearLayout.LayoutParams(280,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                tv.setTextSize(18);
                tv.setTypeface(Typeface.create("sans-serif-smallcaps",Typeface.NORMAL));
                tv.setText(bundle.getString("car"+i)+" ("+bundle.getString("number"+i)+" * "+formatter.format(bundle.getDouble("price"+i))+") ");
                tv.setLayoutParams(params);
                temp.addView(tv);

                carprice[i] = new TextView(this);
                carprice[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                carprice[i].setTextSize(18);
                carprice[i].setTypeface(Typeface.create("sans-serif-smallcaps",Typeface.NORMAL));
                carprice[i].setText("KES "+formatter.format(bundle.getDouble("price"+i) * Integer.parseInt(bundle.getString("number"+i))));
                //editText.setLayoutParams(params);
                temp.addView(carprice[i]);
                rootLayout.addView(temp);

                // tv.setText(tv.getText() + " " + CustomCarHireAdapter.cars.get(i).getType());
            }

        DecimalFormat formatter = new DecimalFormat("#,##0.00");

            TextView t = (TextView)findViewById(R.id.amount);
            t.setText("KES "+formatter.format(total));
        /*for(int i = 0; i < count; i++) {
            Toast.makeText(CarHireBooking.this, bundle.getString("car"+i)+"\n"+bundle.getString("number"+i)+"\n"+bundle.getString("carid"+i)+"\n"+String.valueOf(bundle.getDouble("price"+i)), Toast.LENGTH_LONG).show();
        }*/
    }

}
