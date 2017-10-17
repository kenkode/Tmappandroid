package com.upstridge.tmapp.hotel;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.models.Branch;
import com.upstridge.tmapp.models.Route;
import com.upstridge.tmapp.retrofit.RetrofitInterface;
import com.upstridge.tmapp.retrofit.ServiceGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

public class CheckTimeActivity extends Activity {

    EditText btnpick, dp;
    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;
    String time = "";
    String date = "";

    EditText timepick, t;
    static final int DIALOG_TID = 1;
    int hour, min;
    String url = BASE_URL + "android/branches.php";
    //String url = "http://admin.upstridge.co.ke/android/branches.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_time);

        dp = (EditText) findViewById(R.id.date);
        t = (EditText) findViewById(R.id.time);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        showDate();
        showTimePickerDialog();

        final Spinner area = (Spinner) findViewById(R.id.area);
        Button search = (Button) findViewById(R.id.customers);

        LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorLayout.setVisibility(View.GONE);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.load_cars);

        getBranches(area, errorLayout,progressBar);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date_txt = btnpick.getText().toString();
                String time_txt = timepick.getText().toString();
                String area_txt = area.getSelectedItem().toString();
                if(date_txt.equals("")){
                    btnpick.setError("Please select check-in date");
                }else if(time_txt.equals("")){
                    timepick.setError("Please select check-in time");
                }else {
                    Intent i = new Intent(CheckTimeActivity.this, HotelActivity.class);
                    Bundle b = new Bundle();
                    b.putString("area", area_txt);
                    b.putString("date", date_txt);
                    b.putString("time", time_txt);
                    i.putExtras(b);
                    startActivity(i);
                }
            }
        });

        /*final BranchDownloader d = new BranchDownloader(this, url, dp, t, area, search);
        d.execute();*/
    }

    public void getBranches(final Spinner area, final LinearLayout errorLinear, final ProgressBar loadPrice) {

        final ArrayList<String> rLocations = new ArrayList<>();

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);

        Call<List<Branch>> retroBranches = retrofitInterface.getBranches();

        retroBranches.enqueue(new Callback<List<Branch>>() {
            @Override
            public void onResponse(Call<List<Branch>> call, Response<List<Branch>> response) {
                final List<Branch> route = response.body();
                for (Branch rLoc : route) {
                    rLocations.add(rLoc.getName());
                }
                if(rLocations.size() <= 0) {
                    errorLinear.setVisibility(View.VISIBLE);
                }else {
                    errorLinear.setVisibility(View.GONE);
                }
                loadPrice.setVisibility(View.GONE);
                ArrayAdapter adapter = new ArrayAdapter(CheckTimeActivity.this, android.R.layout.simple_list_item_1, rLocations);
                // apply the Adapter:
                area.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Branch>> call, Throwable t) {
                t.printStackTrace();
                errorLinear.setVisibility(View.VISIBLE);
                loadPrice.setVisibility(View.GONE);
                t.printStackTrace();
                final Snackbar snackbar = Snackbar.make(area, "Something went wrong!", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                        errorLinear.setVisibility(View.GONE);
                        loadPrice.setVisibility(View.VISIBLE);
                        getBranches( area, errorLinear, loadPrice );
                    }
                });
                snackbar.show();
            }
        });


    }

    public void showTimePickerDialog() {
        timepick = (EditText) findViewById(R.id.time);
        timepick.setFocusable(false);
        timepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_TID);
            }
        });
    }

    public void showDate() {
        btnpick = (EditText) findViewById(R.id.date);
        btnpick.setFocusable(false);
        btnpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ID:
                //return new DatePickerDialog(this, dPickerListener, year_x, month_x, day_x);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, dPickerListener, year_x, month_x, day_x);
                Calendar calendar = Calendar.getInstance();

                calendar.add(Calendar.DATE, 0); // Add 0 days to Calendar
                Date newDate = calendar.getTime();
                datePickerDialog.getDatePicker().setMinDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
                return datePickerDialog;

            case DIALOG_TID:
                return new TimePickerDialog(this, kTimePickerListener, hour, min, true);

        }
        if (id == DIALOG_ID) {
            //return new DatePickerDialog(this, dPickerListener, year_x, month_x, day_x);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, dPickerListener, year_x, month_x, day_x);
            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.DATE, 0); // Add 0 days to Calendar
            Date newDate = calendar.getTime();
            datePickerDialog.getDatePicker().setMinDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
            return datePickerDialog;
        }else if (id == DIALOG_TID) {
            return new TimePickerDialog(this, kTimePickerListener, hour, min, false);
        }
        return null;

    }

    protected TimePickerDialog.OnTimeSetListener kTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
            //txtTime1.setText(String.format("%02d:%02d", hourOfDay, minute));
            //hour = hourOfDay % 12;

            t = (EditText) findViewById(R.id.time);
            t.setText(String.format("%02d:%02d", hourOfDay, minute));
            /*t.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                    minute, hourOfDay < 12 ? "am" : "pm"));
            *///t.setText(String.format("%02d:%02d", hourOfDay, minute));
            //Toast.makeText(booking.this,hour+" : "+min,Toast.LENGTH_SHORT).show();
        }
    };

    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            year_x = year;
            month_x = monthOfYear;
            day_x = dayOfMonth;

            Calendar calendar = Calendar.getInstance();
            calendar.set(year_x, month_x, day_x);

            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            String strDate = format.format(calendar.getTime());
            date = strDate;
            dp = (EditText) findViewById(R.id.date);
            dp.setText(strDate);
            //Toast.makeText(booking.this, year_x + "-" + month_x + "-" + day_x, Toast.LENGTH_SHORT).show();
        }
    };

}
