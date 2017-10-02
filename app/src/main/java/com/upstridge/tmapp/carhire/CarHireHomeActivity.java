package com.upstridge.tmapp.carhire;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.upstridge.tmapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CarHireHomeActivity extends AppCompatActivity {

    EditText btnpick, btnpick1, dp, d2;
    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;
    static final int DIALOG_ID1 = 2;
    String time = "";
    String date = "";

    EditText timepick, timepick1, t, t2;
    static final int DIALOG_TID = 1;
    static final int DIALOG_TID1 = 3;
    int hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_hire_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button search = (Button) findViewById(R.id.button);
        dp = (EditText) findViewById(R.id.date);
        t = (EditText) findViewById(R.id.time);
        d2 = (EditText) findViewById(R.id.date1);
        t2 = (EditText) findViewById(R.id.time1);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        showDate();
        showTimePickerDialog();
        showDate1();
        showTime1PickerDialog();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date_txt = btnpick.getText().toString();
                String time_txt = timepick.getText().toString();

                String date_txt1 = btnpick1.getText().toString();
                String time_txt1 = timepick1.getText().toString();

                if(date_txt.equals("")){
                    Toast.makeText(CarHireHomeActivity.this,"Please select travel date",Toast.LENGTH_SHORT).show();
                }else if(time_txt.equals("")){
                    Toast.makeText(CarHireHomeActivity.this,"Please select Start date time",Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(CarHireHomeActivity.this, "Start Date: "+date_txt+" "+time_txt+"\n"+
                            "End Date: "+date_txt1+" "+time_txt1,Toast.LENGTH_LONG).show();

                    Intent i = new Intent(CarHireHomeActivity.this, SelectCarActivity.class);
                    Bundle b = new Bundle();
                    b.putString("startdate", date_txt);
                    b.putString("starttime", time_txt);
                    b.putString("enddate", date_txt1);
                    b.putString("endtime", time_txt1);
                    i.putExtras(b);
                    startActivity(i);
                }
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

    public void showTime1PickerDialog() {
        timepick1 = (EditText) findViewById(R.id.time1);
        timepick1.setFocusable(false);
        timepick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_TID1);
            }
        });
    }

    public void showDate1() {
        btnpick1 = (EditText) findViewById(R.id.date1);
        btnpick1.setFocusable(false);
        btnpick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID1);
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

            case DIALOG_ID1:
                //return new DatePickerDialog(this, dPickerListener, year_x, month_x, day_x);

                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this, dPickerListener1, year_x, month_x, day_x);
                Calendar calendar1 = Calendar.getInstance();

                calendar1.add(Calendar.DATE, 0); // Add 0 days to Calendar
                Date newDate1 = calendar1.getTime();
                datePickerDialog1.getDatePicker().setMinDate(newDate1.getTime()-(newDate1.getTime()%(24*60*60*1000)));
                return datePickerDialog1;

            case DIALOG_TID:
                return new TimePickerDialog(this, kTimePickerListener, hour, min, true);

            case DIALOG_TID1:
                return new TimePickerDialog(this, kTimePickerListener1, hour, min, true);

        }
        if (id == DIALOG_ID) {
            //return new DatePickerDialog(this, dPickerListener, year_x, month_x, day_x);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, dPickerListener, year_x, month_x, day_x);
            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.DATE, 0); // Add 0 days to Calendar
            Date newDate = calendar.getTime();
            datePickerDialog.getDatePicker().setMinDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
            return datePickerDialog;
        }else  if (id == DIALOG_ID1) {
            //return new DatePickerDialog(this, dPickerListener, year_x, month_x, day_x);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, dPickerListener1, year_x, month_x, day_x);
            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.DATE, 0); // Add 0 days to Calendar
            Date newDate = calendar.getTime();
            datePickerDialog.getDatePicker().setMinDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
            return datePickerDialog;
        }else if (id == DIALOG_TID) {
            return new TimePickerDialog(this, kTimePickerListener, hour, min, false);
        }else if (id == DIALOG_TID1) {
            return new TimePickerDialog(this, kTimePickerListener1, hour, min, false);
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

    protected TimePickerDialog.OnTimeSetListener kTimePickerListener1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
            //txtTime1.setText(String.format("%02d:%02d", hourOfDay, minute));
            //hour = hourOfDay % 12;

            t2 = (EditText) findViewById(R.id.time1);
            t2.setText(String.format("%02d:%02d", hourOfDay, minute));
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

    private DatePickerDialog.OnDateSetListener dPickerListener1 = new DatePickerDialog.OnDateSetListener() {
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
            d2 = (EditText) findViewById(R.id.date1);
            d2.setText(strDate);
            //Toast.makeText(booking.this, year_x + "-" + month_x + "-" + day_x, Toast.LENGTH_SHORT).show();
        }
    };

}
