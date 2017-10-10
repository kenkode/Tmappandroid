package com.upstridge.tmapp.carhire;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.upstridge.tmapp.R;
import com.upstridge.tmapp.adapters.CustomCarHireAdapter;
import com.upstridge.tmapp.bus.HideHintEditText;
import com.upstridge.tmapp.bus.StartActivity;
import com.upstridge.tmapp.models.Hire;
import com.upstridge.tmapp.retrofit.RetrofitInterface;
import com.upstridge.tmapp.retrofit.ServiceGenerator;

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
        final String organization = bundle.getString("organization");
        final int count = bundle.getInt("count");

        String[] types = new String[count];
        int[] nums = new int[count];
        double[] amounts = new double[count];

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

            types[i] = bundle.getString("car"+i);
            nums[i] = Integer.parseInt(bundle.getString("number"+i));
            amounts[i] = bundle.getDouble("price"+i);

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

        final ProgressDialog progressDialog = new ProgressDialog(CarHireBooking.this);

        hireCar(sdate, stime, edate, etime, location, organization, firstname.getText().toString()
                , lastname.getText().toString(), phone.getText().toString(), email.getText().toString(),
                idno.getText().toString(), mode.getSelectedItem().toString(), types, nums, amounts, progressDialog);

        /*for(int i = 0; i < count; i++) {
            Toast.makeText(CarHireBooking.this, bundle.getString("car"+i)+"\n"+bundle.getString("number"+i)+"\n"+bundle.getString("carid"+i)+"\n"+String.valueOf(bundle.getDouble("price"+i)), Toast.LENGTH_LONG).show();
        }*/
    }

    private void hireCar(final String sdate, final String stime, final String edate, final String etime, final String location,
                         final String organization, final String firstname, final String lastname, final String phone,
                         final String email, final String idno, final String mode, final String[] types, final int[] nums,
                         final double[] amounts, final ProgressDialog progressDialog) {

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);
        retrofit2.Call<Hire> hcare = retrofitInterface.hireCar(sdate, stime, edate, etime, location, organization, firstname
                , lastname, phone, email, idno, mode, types, nums, amounts);

        hcare.enqueue(new retrofit2.Callback<Hire>() {
            @Override
            public void onResponse(retrofit2.Call<Hire> call, retrofit2.Response<Hire> response) {
                //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
               // Log.e("Tag", userid);
                Hire hire = response.body();
                if (hire.getSuccess().equals("Book Successful")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CarHireBooking.this);
                    builder.setMessage("Car Hire Successful!")
                            .setTitle("Success Message")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    Intent i = new Intent(CarHireBooking.this, StartActivity.class);
                    startActivity(i);

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(CarHireBooking.this);
                    builder.setMessage("Something went wrong!")
                            .setTitle("Error Message")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                //confirmPin(user, progressDialog);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(retrofit2.Call<Hire> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
                //Log.e("TAG", userid);
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(book, "Something went wrong", Snackbar.LENGTH_LONG);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        hireCar(sdate, stime, edate, etime, location, organization, firstname
                                , lastname, phone, email, idno, mode, types, nums, amounts, progressDialog);
                    }
                });
                snackbar.show();
            }
        });

    }

}
