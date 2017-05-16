package com.upstridge.tmapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.BatchUpdateException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookingActivity extends Activity {

    String urlAddress = "http://192.168.56.1/tmapp/android/booking.php";
    EditText firstname,lastname,email,phone,idno;
    Spinner fare,mode;
    Button book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Bundle bundle = getIntent().getExtras();
        final String organization = bundle.getString("organization");
        final String vehicle = bundle.getString("vid");
        final String destination = bundle.getString("destination");
        final String date = bundle.getString("date");
        final String time = bundle.getString("time");
        final String origin = bundle.getString("origin");
        final String arrival = bundle.getString("arrival");
        final String departure = bundle.getString("departure");
        final String vip = bundle.getString("vip");
        final String economic = bundle.getString("economic");
        String firstclassapply = bundle.getString("firstclassapply");

        String[] faretypes=new String[]{"VIP","Economic"};

        String[] modes=new String[]{"Mpesa","Airtel Money","VISA"};

        if(firstclassapply.equals("1")){
            faretypes=new String[]{"Vip Fare : KES "+vip,"Economic Fare : KES "+economic};
        }else{
            faretypes=new String[]{"Economic Fare : KES "+economic};
        }

        firstname = (EditText)findViewById(R.id.firstname);
        lastname = (EditText)findViewById(R.id.lastname);
        email = (EditText)findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.phone);
        idno = (EditText)findViewById(R.id.idno);
        final String em = email.getText().toString();

        fare=(Spinner) findViewById(R.id.fare);
        ArrayAdapter<String> fareArray= new ArrayAdapter<String>(BookingActivity.this,android.R.layout.simple_spinner_item, faretypes);
        fareArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fare.setAdapter(fareArray);

        mode=(Spinner) findViewById(R.id.mode);
        ArrayAdapter<String> modeArray= new ArrayAdapter<String>(BookingActivity.this,android.R.layout.simple_spinner_item, modes);
        modeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mode.setAdapter(modeArray);

        book = (Button)findViewById(R.id.book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstname.getText().toString().trim().equals("")){
                    firstname.setError("Please insert your first name");
                }else if(lastname.getText().toString().trim().equals("")){
                    lastname.setError("Please insert your last name");
                }else if(phone.getText().toString().trim().equals("")){
                    phone.setError("Please insert your phone number");
                }else if(email.getText().toString().trim().equals("")){
                    email.setError("Please insert your email");
                }else if (!email.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                    email.setError("Please insert a valid email address");
                }else if(idno.getText().toString().trim().equals("")){
                    idno.setError("Please insert your national identity number");
                }else {
                    Sender s = new Sender(BookingActivity.this, urlAddress, organization, vehicle, destination, origin, date, time, arrival, departure, fare, mode, firstname, lastname, email, phone, idno);
                    s.execute();
                }
            }
        });
    }

    public boolean isValidEmail(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
