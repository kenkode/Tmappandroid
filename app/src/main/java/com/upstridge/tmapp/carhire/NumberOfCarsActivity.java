package com.upstridge.tmapp.carhire;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.adapters.CustomCarHireAdapter;
import com.upstridge.tmapp.bus.HideHintEditText;

import java.util.ArrayList;

public class NumberOfCarsActivity extends AppCompatActivity {

    TextView [] error;
    EditText [] num;
    Button select;
    ArrayList<String> cars= new ArrayList<String>();
    ArrayList<String> carids= new ArrayList<String>();
    ArrayList<String> number = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_of_cars);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        final String sdate = bundle.getString("startdate");
        final String stime = bundle.getString("starttime");
        final String edate = bundle.getString("enddate");
        final String etime = bundle.getString("endtime");
        final String location = bundle.getString("location");
        final String organization = bundle.getString("organization");


        select = (Button)findViewById(R.id.select);

       //TextView tv = (TextView) findViewById(R.id.tv);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isnumEdited() == false) {
                    for (int i = 0; i < CustomCarHireAdapter.cars.size(); i++) {
                        if (CustomCarHireAdapter.cars.get(i).getSelected()) {

                            if (num[i].getText().toString().trim().equals("")) {
                                num[i].setError("Please insert number of cars to hire");
                            }

                        }
                    }
                } else {
                    for (int i = 0; i < CustomCarHireAdapter.cars.size(); i++) {
                        if (CustomCarHireAdapter.cars.get(i).getSelected()) {
                            cars.add(CustomCarHireAdapter.cars.get(i).getType());
                            carids.add(CustomCarHireAdapter.cars.get(i).getId());
                            number.add(num[i].getText().toString());
                        }
                    }
                    Intent intent = new Intent(NumberOfCarsActivity.this, CarHireBooking.class);
                    Bundle b = new Bundle();
                    b.putString("startdate", sdate);
                    b.putString("starttime", stime);
                    b.putString("enddate", edate);
                    b.putString("endtime", etime);
                    b.putString("location", location);
                    b.putString("organization", organization);
                    b.putInt("count", CustomCarHireAdapter.cars.size());
                    for (int i = 0; i < CustomCarHireAdapter.cars.size(); i++) {
                        if (CustomCarHireAdapter.cars.get(i).getSelected()) {
                            b.putString("car"+i, CustomCarHireAdapter.cars.get(i).getType());
                            b.putString("carid"+i, CustomCarHireAdapter.cars.get(i).getId());
                            b.putString("number"+i, num[i].getText().toString());
                            b.putDouble("price"+i, CustomCarHireAdapter.cars.get(i).getPrice());
                        }
                    }
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });


        RelativeLayout[] relativeLayout = new RelativeLayout[CustomCarHireAdapter.cars.size()];
        num = new EditText[CustomCarHireAdapter.cars.size()];
        //cartype = new TextView[CustomCarHireAdapter.cars.size()];
        //seaterror = new TextView[CustomCarHireAdapter.cars.size()];

        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.rel);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < CustomCarHireAdapter.cars.size(); i++){
            if(CustomCarHireAdapter.cars.get(i).getSelected()) {
                LinearLayout temp = new LinearLayout(this);
                temp.setOrientation(LinearLayout.HORIZONTAL);
                temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,3));
                /*LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rootLayout.getLayoutParams();
                params.setMargins(0, 0, 0, 30);*/
                //relativeLayout[i] = new RelativeLayout(this);
                //relativeLayout[i].setId(R.id.dynamic+(i+1));
                // Defining the RelativeLayout layout parameters.
                // In this case I want to fill its parent

                // Creating a new TextView
                TextView tv = new TextView(this);
                tv.setLayoutParams(new LinearLayout.LayoutParams(300,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                tv.setTextSize(18);
                tv.setTypeface(Typeface.create("sans-serif-smallcaps",Typeface.NORMAL));
                tv.setText(CustomCarHireAdapter.cars.get(i).getType());
                //tv.setLayoutParams(params);
                temp.addView(tv);

                num[i] = new HideHintEditText(this, "Cars to Hire");
                num[i].setId(R.id.ctype);
                num[i].setInputType(InputType.TYPE_CLASS_NUMBER);
                num[i].setHint("Cars to Hire");
                num[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                //editText.setLayoutParams(params);
                temp.addView(num[i]);
                rootLayout.addView(temp);

               // tv.setText(tv.getText() + " " + CustomCarHireAdapter.cars.get(i).getType());
            }

        }
    }

    public boolean isnumEdited(){

        for (int i = 0; i < CustomCarHireAdapter.cars.size(); i++) {
            if (num[i].getText().toString().trim().equals("")) {
                return false;
            }
        }
        // we reached this point so all edit texts have been given input
        return true;
    }

}
