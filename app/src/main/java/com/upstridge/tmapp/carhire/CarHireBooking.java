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
import com.upstridge.tmapp.config.GsonHelper;
import com.upstridge.tmapp.models.Booking;
import com.upstridge.tmapp.models.Hire;
import com.upstridge.tmapp.retrofit.RetrofitInterface;
import com.upstridge.tmapp.retrofit.ServiceGenerator;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarHireBooking extends AppCompatActivity {

    Spinner mode;
    EditText firstname,lastname,email,phone,idno;
    TextView amount;
    double total = 0;
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

        long diff = 0;
        long diffDays = 0;

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(sdate);
            d2 = format.parse(edate);
            diff = d2.getTime() - d1.getTime();
            diffDays = (diff / (24 * 60 * 60 * 1000)) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        final String[] types = new String[count];
        final int[] nums = new int[count];
        final double[] amounts = new double[count];

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


        for (int i = 0; i < count; i++){

            types[i] = bundle.getString("car"+i);
            nums[i] = Integer.parseInt(bundle.getString("number"+i));
            amounts[i] = bundle.getDouble("price"+i);

            total += (bundle.getDouble("price"+i) * Integer.parseInt(bundle.getString("number"+i)) * diffDays);

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
            TextView t1 = (TextView)findViewById(R.id.textView20);
            t.setText("KES "+formatter.format(total));
            t1.setText("Total * "+diffDays+" days");

        final ProgressDialog progressDialog = new ProgressDialog(CarHireBooking.this);

        book = (Button)findViewById(R.id.book);

        final long finalDiffDays = diffDays;
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstname.getText().toString().trim().equalsIgnoreCase("")) {
                    firstname.setError("Please insert your first name");
                }else if (lastname.getText().toString().trim().equalsIgnoreCase("")) {
                    lastname.setError("Please insert your last name");
                }else if (email.getText().toString().trim().equalsIgnoreCase("")) {
                    email.setError("Please insert your email address");
                }else if(!email.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
                    email.setError("Please insert a valid email address");
                }else if (phone.getText().toString().trim().equalsIgnoreCase("")) {
                    phone.setError("Please insert your phone number");
                }else if (idno.getText().toString().trim().equalsIgnoreCase("")) {
                    idno.setError("Please insert your national identity number / passport number");
                }else {
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Car Hiring...");
                    progressDialog.show();
                    hireCar(sdate, stime, edate, etime, location, organization, firstname.getText().toString()
                            , lastname.getText().toString(), phone.getText().toString(), email.getText().toString(),
                            idno.getText().toString(), mode.getSelectedItem().toString(), types, nums, amounts, finalDiffDays,
                            total, progressDialog);
                }
            }
        });



        /*for(int i = 0; i < count; i++) {
            Toast.makeText(CarHireBooking.this, bundle.getString("car"+i)+"\n"+bundle.getString("number"+i)+"\n"+bundle.getString("carid"+i)+"\n"+String.valueOf(bundle.getDouble("price"+i)), Toast.LENGTH_LONG).show();
        }*/
    }

    private void hireCar(final String sdate, final String stime, final String edate, final String etime, final String location,
                         final String organization, final String firstname, final String lastname, final String phone,
                         final String email, final String idno, final String mode, final String[] types,
                         final int[] nums, final double[] amounts, long finalDiffDays,
                         final double amount, final ProgressDialog progressDialog) {

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);
        Gson gson = GsonHelper.getBuilder().create();
        /*Hire h = new Hire(sdate, stime, edate, etime, location, organization, firstname
                , lastname, phone, email, idno, mode, types, nums, amounts, finalDiffDays, amount);
        final String userJson = gson.toJson(h);*/
       // Log.d("Result",userJson);
        Call<Hire> hcare = retrofitInterface.hireCar(sdate, stime, edate, etime, location, organization, firstname
                , lastname, phone, email, idno, mode, Arrays.toString(types), Arrays.toString(nums), Arrays.toString(amounts), finalDiffDays, amount);

        hcare.enqueue(new Callback<Hire>() {
            @Override
            public void onResponse(Call<Hire> call, Response<Hire> response) {
                //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
               // Log.e("Tag", userid);
                Hire booking = response.body();
                if (booking.getSuccess().equals("Book Successful")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CarHireBooking.this);
                    builder.setMessage("Booking Successful... Your booking Details have been sent to your email address!")
                            .setTitle("Success Message")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    /*Intent i = new Intent(CarHireBooking.this, StartActivity.class);
                    startActivity(i);*/

                }else{
                    Toast.makeText(CarHireBooking.this, booking.getSuccess(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(CarHireBooking.this);
                    //builder.setMessage(booking.getSuccess())
                    builder.setMessage("An error occured during booking...please try again!")
                            .setTitle("Error Message")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                //confirmPin(user, progressDialog);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Hire> call, Throwable t) {
                t.printStackTrace();

                //Log.e("TAG", userJson);
                progressDialog.dismiss();
                Toast.makeText(CarHireBooking.this, "Something went wrong...Please try again later!", Toast.LENGTH_LONG).show();
                /*Snackbar snackbar = Snackbar.make(book, "Something went wrong", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        hireCar(sdate, stime, edate, etime, location, organization, firstname
                                , lastname, phone, email, idno, mode, types, nums, amounts, progressDialog);
                    }
                });
                snackbar.show();*/
            }
        });

    }

}
