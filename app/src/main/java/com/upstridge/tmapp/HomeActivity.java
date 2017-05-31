package com.upstridge.tmapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends Activity {

    EditText btnpick, dp;
    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;
    String time = "";
    String date = "";

    EditText timepick, t;
    static final int DIALOG_TID = 1;
    int hour, min;
    //String url = "http://192.168.56.1/tmapp/android/destination.php";
    String url = "http://admin.upstridge.co.ke/android/destination.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Spinner from = (Spinner) findViewById(R.id.from);
        Spinner to = (Spinner) findViewById(R.id.to);
        Button search = (Button) findViewById(R.id.button);
        dp = (EditText) findViewById(R.id.date);
        t = (EditText) findViewById(R.id.time);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        showDate();
        showTimePickerDialog();

        final HomeDownloader d = new HomeDownloader(this, url, from, to, search, dp, t);
        d.execute();
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
