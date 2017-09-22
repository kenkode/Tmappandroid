package com.upstridge.tmapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;

public class BookingActivity extends Activity {

    String urlAddress = "http://10.0.2.2/tmapp/android/booking.php";
    //String urlAddress = "http://admin.upstridge.co.ke/android/booking.php";
    TextView seaterror [];
    EditText firstname,lastname,email,phone,idno;
    EditText [] fnametxt,lnametxt,emailtxt,phonetxt,idtxt;
    Spinner[] amounttxt,seattxt;
    Spinner fare,mode,seat;
    Button book;
    String vehiclename = "";
    ArrayList<String> seats= new ArrayList<String>();
    ArrayList<String> fnamevalues = new ArrayList<>();
    ArrayList<String> lnamevalues = new ArrayList<>();
    ArrayList<String> phonevalues = new ArrayList<>();
    ArrayList<String> emailvalues = new ArrayList<>();
    ArrayList<String> seatvalues = new ArrayList<>();
    ArrayList<String> farevalues = new ArrayList<>();
    ArrayList<String> idnovalues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Bundle bundle = getIntent().getExtras();
        final String organization = bundle.getString("organization");
        final String vehicle = bundle.getString("vid");
        vehiclename = bundle.getString("vehicle");
        final String destination = bundle.getString("destination");
        final String date = bundle.getString("date");
        final String time = bundle.getString("time");
        final String origin = bundle.getString("origin");
        final String arrival = bundle.getString("arrival");
        final String departure = bundle.getString("departure");
        final String vip = bundle.getString("vip");
        final String economic = bundle.getString("economic");
        final String type = "Travel";
        String firstclassapply = bundle.getString("firstclassapply");
        seats=  getIntent().getStringArrayListExtra("seats");

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

        seat=(Spinner) findViewById(R.id.seats);
        ArrayAdapter<String> seatArray= new ArrayAdapter<String>(BookingActivity.this,android.R.layout.simple_spinner_item, seats);
        seatArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seat.setAdapter(seatArray);

        mode=(Spinner) findViewById(R.id.mode);
        ArrayAdapter<String> modeArray= new ArrayAdapter<String>(BookingActivity.this,android.R.layout.simple_spinner_item, modes);
        modeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mode.setAdapter(modeArray);



        if(seats.size() > 1) {

            RelativeLayout rootLayout = (RelativeLayout)findViewById(R.id.booking);
            RelativeLayout bottomLayout = (RelativeLayout)findViewById(R.id.last);
            RelativeLayout[] relativeLayout = new RelativeLayout[seats.size()-1];
            fnametxt = new EditText[seats.size()-1];
            lnametxt = new EditText[seats.size()-1];
            phonetxt = new EditText[seats.size()-1];
            emailtxt = new EditText[seats.size()-1];
            seattxt = new Spinner[seats.size()-1];
            amounttxt = new Spinner[seats.size()-1];
            idtxt = new EditText[seats.size()-1];
            seaterror = new TextView[seats.size()-1];
            //RelativeLayout relativeLayout = new RelativeLayout(this);
            LinearLayout lp = new LinearLayout(this);

            for (int i = 0; i < seats.size()-1; i++) {
                relativeLayout[i] = new RelativeLayout(this);
                relativeLayout[i].setId(R.id.dynamic+(i+1));
                // Defining the RelativeLayout layout parameters.
                // In this case I want to fill its parent
                RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                param.addRule(RelativeLayout.BELOW,R.id.first);
                param.setMargins(0, 60, 0, 30);
                relativeLayout[i].setLayoutParams(param);

                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                TextView title = new TextView(this);
                title.setId(R.id.title);
                title.setTextSize(16);
                title.setTextColor(Color.rgb(17, 189, 255));
                title.setText((i + 2) + " Person Details");
                title.setGravity(Gravity.CENTER);
                param.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                param.setMargins(10, 0, 0, 30);

                title.setLayoutParams(param);
                relativeLayout[i].addView(title);

                param = new RelativeLayout.LayoutParams(
                        350,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                // Creating a new TextView
                TextView fname = new TextView(this);
                fname.setId(R.id.fname);
                fname.setText("First Name:");
                fname.setTextSize(15);
                param.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.BELOW, title.getId());
                param.setMargins(30, 0, 0, 30);
                fname.setLayoutParams(param);
                relativeLayout[i].addView(fname);

                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);



                fnametxt[i] = new HideHintEditText(this, "First name");
                //fnametxt[i].setWidth(490);
                fnametxt[i].setId(R.id.fnametxt);
                fnametxt[i].setHint("First name");
                param.addRule(RelativeLayout.RIGHT_OF,fname.getId());
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_BASELINE,
                        fname.getId());
                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        fname.getId());
                fnametxt[i].setLayoutParams(param);
                relativeLayout[i].addView(fnametxt[i]);

                param = new RelativeLayout.LayoutParams(
                        350,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                // Creating a new TextView
                TextView lname = new TextView(this);
                lname.setId(R.id.lname);
                lname.setText("Last Name:");
                lname.setTextSize(15);

                param.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.BELOW, fname.getId());
                param.setMargins(30, 0, 0, 30);
                lname.setLayoutParams(param);
                relativeLayout[i].addView(lname);

                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                lnametxt[i] = new HideHintEditText(this, "Last name");
                //lnametxt[i].setWidth(490);
                lnametxt[i].setId(R.id.lnametxt);
                lnametxt[i].setHint("Last name");
                param.addRule(RelativeLayout.RIGHT_OF,lname.getId());
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_BASELINE,
                        lname.getId());
                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        lname.getId());
                lnametxt[i].setLayoutParams(param);
                relativeLayout[i].addView(lnametxt[i]);


                param = new RelativeLayout.LayoutParams(
                        350,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                // Creating a new TextView
                TextView phoneno = new TextView(this);
                phoneno.setId(R.id.phoneno);
                phoneno.setText("Phone No:");
                phoneno.setTextSize(15);
                param.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.BELOW, lname.getId());
                param.setMargins(30, 0, 0, 30);
                phoneno.setLayoutParams(param);
                relativeLayout[i].addView(phoneno);

                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                phonetxt[i] = new HideHintEditText(this, "Phone Number");
                //phonetxt[i].setWidth(490);
                phonetxt[i].setId(R.id.phonetxt);
                phonetxt[i].setHint("Phone Number");
                phonetxt[i].setInputType(InputType.TYPE_CLASS_PHONE);
                param.addRule(RelativeLayout.RIGHT_OF,phoneno.getId());
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_BASELINE,
                        phoneno.getId());
                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        phoneno.getId());
                phonetxt[i].setLayoutParams(param);
                relativeLayout[i].addView(phonetxt[i]);

                param = new RelativeLayout.LayoutParams(
                        350,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                // Creating a new TextView
                TextView emailaddress = new TextView(this);
                emailaddress.setId(R.id.emailaddress);
                emailaddress.setText("Email Address:");
                emailaddress.setTextSize(15);
                param.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.BELOW, phoneno.getId());
                param.setMargins(30, 0, 0, 30);
                emailaddress.setLayoutParams(param);
                relativeLayout[i].addView(emailaddress);

                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                emailtxt[i] = new HideHintEditText(this, "Email Address");
                //emailtxt[i].setWidth(490);
                emailtxt[i].setId(R.id.emailtxt);
                emailtxt[i].setHint("Email Address");
                emailtxt[i].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                param.addRule(RelativeLayout.RIGHT_OF,emailaddress.getId());
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_BASELINE,
                        emailaddress.getId());
                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        emailaddress.getId());
                emailtxt[i].setLayoutParams(param);
                relativeLayout[i].addView(emailtxt[i]);

                param = new RelativeLayout.LayoutParams(
                        350,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                // Creating a new TextView
                TextView idnumber = new TextView(this);
                idnumber.setId(R.id.idpass);
                idnumber.setText("Identity No /\nPassport No:");
                idnumber.setTextSize(15);
                param.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.BELOW, emailaddress.getId());
                param.setMargins(30, 0, 0, 30);
                idnumber.setLayoutParams(param);
                relativeLayout[i].addView(idnumber);

                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                idtxt[i] = new HideHintEditText(this, "Identity No / Passport No");
                //idtxt[i].setWidth(490);
                idtxt[i].setId(R.id.idpasstxt);
                param.addRule(RelativeLayout.RIGHT_OF,idnumber.getId());
                idtxt[i].setHint("Identity No / Passport No");
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_TOP,
                        idnumber.getId());
                idtxt[i].setLayoutParams(param);
                relativeLayout[i].addView(idtxt[i]);

                param = new RelativeLayout.LayoutParams(
                        350,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                // Creating a new TextView
                TextView seatno = new TextView(this);
                seatno.setId(R.id.seatno);
                seatno.setText("Select Seat:");
                seatno.setTextSize(15);
                param.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.BELOW, idnumber.getId());
                param.setMargins(30, 0, 0, 30);
                seatno.setLayoutParams(param);
                relativeLayout[i].addView(seatno);

                param = new RelativeLayout.LayoutParams(
                        FILL_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                seattxt[i] = new Spinner(this);
                seattxt[i].setId(R.id.seattxt);
                param.addRule(RelativeLayout.RIGHT_OF,seatno.getId());
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_BASELINE,
                        seatno.getId());
                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        seatno.getId());
                seattxt[i].setLayoutParams(param);
                seattxt[i].setAdapter(seatArray);
                relativeLayout[i].addView(seattxt[i]);


                param = new RelativeLayout.LayoutParams(0,0);

                seaterror[i] = new TextView(this);
                seaterror[i].setId(R.id.seatInvisibleError);
                param.setMargins(0,0,0,0);
                seaterror[i].setPadding(0,0,10,0);
                seaterror[i].setFocusable(true);
                seaterror[i].setFocusableInTouchMode(true);
                param.addRule(RelativeLayout.ALIGN_RIGHT, seattxt[i].getId());
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);

                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        seattxt[i].getId());
                seaterror[i].setLayoutParams(param);
                relativeLayout[i].addView(seaterror[i]);

                param = new RelativeLayout.LayoutParams(
                        350,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                // Creating a new TextView
                TextView amount = new TextView(this);
                amount.setId(R.id.amount);
                amount.setText("Select Fare:");
                amount.setTextSize(15);
                param.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.BELOW, seatno.getId());
                param.setMargins(30, 0, 0, 30);
                amount.setLayoutParams(param);
                relativeLayout[i].addView(amount);

                param = new RelativeLayout.LayoutParams(
                        FILL_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                amounttxt[i] = new Spinner(this);
                amounttxt[i].setId(R.id.amounttxt);
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_BASELINE,
                        amount.getId());
                param.addRule(RelativeLayout.RIGHT_OF,amount.getId());
                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        amount.getId());
                amounttxt[i].setLayoutParams(param);
                amounttxt[i].setAdapter(fareArray);
                relativeLayout[i].addView(amounttxt[i]);

                if(i == 0 ){
                    param = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);

                    param.addRule(RelativeLayout.BELOW,R.id.first);
                    param.setMargins(0, 60, 0, 30);
                    relativeLayout[i].setLayoutParams(param);
                }else {
                    param = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    param.addRule(RelativeLayout.BELOW, relativeLayout[i-1].getId());
                    param.setMargins(0, 10, 0, 10);
                    relativeLayout[i].setLayoutParams(param);
                }
                rootLayout.addView(relativeLayout[i]);
            }

            RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            param1.addRule(RelativeLayout.BELOW, R.id.dynamic+(seats.size()-1));
            bottomLayout.setLayoutParams(param1);
            // Defining the layout parameters of the TextView

        }
        book = (Button)findViewById(R.id.book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!firstname.getText().toString().trim().equals("")){
                    Intent i = new Intent(getApplicationContext(), RequestTaxiActivity.class);

                    startActivity(i);
                }else {
                    fnamevalues.clear();
                    lnamevalues.clear();
                    emailvalues.clear();
                    phonevalues.clear();
                    idnovalues.clear();
                    seatvalues.clear();
                    farevalues.clear();

                    if (firstname.getText().toString().trim().equals("")) {
                        firstname.setError("Please insert your first name");
                    } else if (lastname.getText().toString().trim().equals("")) {
                        lastname.setError("Please insert your last name");
                    } else if (phone.getText().toString().trim().equals("")) {
                        phone.setError("Please insert your phone number");
                    } else if (email.getText().toString().trim().equals("")) {
                        email.setError("Please insert your email");
                    } else if (!email.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                        email.setError("Please insert a valid email address");
                    } else if (idno.getText().toString().trim().equals("")) {
                        idno.setError("Please insert your national identity number");
                    } else if (idno.getText().toString().trim().equals("")) {
                        idno.setError("Please insert your national identity number / Passport number");
                    } else if (isfnameEdited() == false) {
                        for (int i = 0; i < seats.size() - 1; i++) {
                            if (fnametxt[i].getText().toString().trim().equals("")) {
                                fnametxt[i].setError("Please insert " + (i + 2) + " person`s first name");
                            }
                        }
                    } else if (islnameEdited() == false) {
                        for (int i = 0; i < seats.size() - 1; i++) {
                            if (lnametxt[i].getText().toString().trim().equals("")) {
                                lnametxt[i].setError("Please insert " + (i + 2) + " person`s last name");
                            }
                        }
                    } else if (isphoneEdited() == false) {
                        for (int i = 0; i < seats.size() - 1; i++) {
                            if (phonetxt[i].getText().toString().trim().equals("")) {
                                phonetxt[i].setError("Please insert " + (i + 2) + " person`s phone number");
                            }
                        }
                    } else if (isemailEdited() == false) {
                        for (int i = 0; i < seats.size() - 1; i++) {
                            if (emailtxt[i].getText().toString().trim().equals("")) {
                                emailtxt[i].setError("Please insert " + (i + 2) + " person`s email");
                            }
                        }
                    } else if (isemailValid() == false) {
                        for (int i = 0; i < seats.size() - 1; i++) {
                            if (!emailtxt[i].getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                                emailtxt[i].setError("Please insert a valid email address for " + (i + 2) + " person");
                            }
                        }
                    } else if (isidnoEdited() == false) {
                        for (int i = 0; i < seats.size() - 1; i++) {
                            if (idtxt[i].getText().toString().trim().equals("")) {
                                idtxt[i].setError("Please insert " + (i + 2) + " person`s national identity number / Passport number");
                            }
                        }
                    } else {
                        fnamevalues.add(firstname.getText().toString());
                        lnamevalues.add(lastname.getText().toString());
                        phonevalues.add(phone.getText().toString());
                        emailvalues.add(email.getText().toString());
                        idnovalues.add(idno.getText().toString());
                        seatvalues.add(seat.getSelectedItem().toString());
                        farevalues.add(fare.getSelectedItem().toString());

                        for (int i = 0; i < seats.size() - 1; i++) {
                            if (!fnametxt[i].getText().toString().trim().equals("")) {
                                fnamevalues.add(fnametxt[i].getText().toString());
                            }
                            if (!lnametxt[i].getText().toString().trim().equals("")) {
                                lnamevalues.add(lnametxt[i].getText().toString());
                            }
                            if (!phonetxt[i].getText().toString().trim().equals("")) {
                                phonevalues.add(phonetxt[i].getText().toString());
                            }
                            if (!emailtxt[i].getText().toString().trim().equals("") && emailtxt[i].getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                                emailvalues.add(emailtxt[i].getText().toString());
                            }
                            if (!idtxt[i].getText().toString().trim().equals("")) {
                                idnovalues.add(idtxt[i].getText().toString());
                            }
                            seatvalues.add(seattxt[i].getSelectedItem().toString());
                            farevalues.add(amounttxt[i].getSelectedItem().toString());
                        }

                        StringBuilder builder = new StringBuilder();
                        for (String seat : seatvalues) {
                            builder.append(seat + ",");
                        }
                        //Toast.makeText(BookingActivity.this, builder, Toast.LENGTH_LONG).show();

                        if (isSeatEdited(seatvalues) == false) {
                            for (int i = 0; i < seatvalues.size(); i++) {
                                for (int k = i + 1; k < seatvalues.size(); k++) {
                                    if (seatvalues.get(i) == seatvalues.get(k)) {
                                        if (seattxt[i].getSelectedItem().toString().equals(seatvalues.get(i))) {
                                            //seattxt[i].setError("Please insert your first name");

                                       /* TextView errorText = (TextView) seattxt[i].getSelectedView();
                                        errorText.setError("You have selected " + seats.get(i) + " more than once");
                                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                                        errorText.setText("You have selected " + seats.get(i) + " more than once");//changes the selected item text to this
                                        Toast.makeText(BookingActivity.this, "You have selected " + seats.get(i) + " more than once", Toast.LENGTH_LONG).show();*/

                                            View view = seattxt[i].getSelectedView();

                                            // Set TextView in Secondary Unit spinner to be in error so that red (!) icon
                                            // appears, and then shake control if in error
                                            TextView seatListItem = (TextView) view;

                                            seatListItem.setError("You have selected " + seats.get(i) + " more than once");
                                            seatListItem.requestFocus();


                                            seaterror[i].requestFocus();
                                            seaterror[i].setError("You have selected " + seats.get(i) + " more than once");
                                        } else {
                                            View view = seattxt[i].getSelectedView();

                                            // Set TextView in Secondary Unit spinner to be in error so that red (!) icon
                                            // appears, and then shake control if in error
                                            TextView seatListItem = (TextView) view;

                                            seatListItem.setError("");
                                            seatListItem.requestFocus();


                                            seaterror[i].requestFocus();
                                            seaterror[i].setError("");
                                        }
                                    }
                                }
                            }
                        } else {
                            Sender s = new Sender(BookingActivity.this, urlAddress, organization, vehicle, destination, origin,date, time, arrival, departure,type, farevalues, mode, seatvalues, fnamevalues, lnamevalues, emailvalues, phonevalues, idnovalues);
                            s.execute();
                        }
                    }
                }
            }
        });
    }

    public boolean isfnameEdited(){

        for (int i = 0; i < seats.size()-1; i++) {
            if (fnametxt[i].getText().toString().trim().equals("")) {
                return false;
            }
        }
        // we reached this point so all edit texts have been given input
        return true;
    }

    public boolean islnameEdited(){

        for (int i = 0; i < seats.size()-1; i++) {
            if(lnametxt[i].getText().toString().trim().equals("")){
                return false;
            }
        }
        // we reached this point so all edit texts have been given input
        return true;
    }

    public boolean isphoneEdited(){

        for (int i = 0; i < seats.size()-1; i++) {
            if(phonetxt[i].getText().toString().trim().equals("")){
                return false;
            }
        }
        // we reached this point so all edit texts have been given input
        return true;
    }

    public boolean isemailEdited(){

        for (int i = 0; i < seats.size()-1; i++) {
            if(emailtxt[i].getText().toString().trim().equals("")){
                return false;
            }
        }
        // we reached this point so all edit texts have been given input
        return true;
    }

    public boolean isemailValid(){

        for (int i = 0; i < seats.size()-1; i++) {
            if(!emailtxt[i].getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
                return false;
            }
        }
        // we reached this point so all edit texts have been given input
        return true;
    }

    public boolean isidnoEdited(){

        for (int i = 0; i < seats.size()-1; i++) {
            if(idtxt[i].getText().toString().trim().equals("")) {
                return false;
            }
        }
        // we reached this point so all edit texts have been given input
        return true;
    }

    public boolean isSeatEdited(ArrayList seatvalues){

        for (int i = 0; i < seatvalues.size(); i++) {
            for (int k = i + 1; k < seatvalues.size(); k++) {
                if (seatvalues.get(i) == seatvalues.get(k)) {
                    return false;
                }
            }
        }

        /*for (int i = 0; i < seats.size()-1; i++) {
            if(seattxt[i].getSelectedItem().toString().trim().equals(seattxt[i+1].getSelectedItem().toString()) || seattxt[i].getSelectedItem().toString().trim().equals(seat.getSelectedItem().toString())) {
                return false;
            }
        }*/
        // we reached this point so all edit texts have been given input
        return true;
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

    private void sendEmail() {
        //Getting content for email
        String emailaddress = email.getText().toString().trim();
        String subject = "Booking Details";

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String strDate = sdf.format(c.getTime());

        String message = "Hi "+firstname.getText().toString()+" "+lastname.getText().toString()+",\n\n"
                       + "This is a confirmation that you have successfully booked "+vehiclename+" on "+strDate+"\n\n"
                       + "Your booking details are:\n"
                       + "1. First Name           : "+firstname.getText().toString()+"\n"
                       + "2. Last Name            : "+lastname.getText().toString()+"\n"
                       + "3. Phone Number         : "+phone.getText().toString()+"\n"
                       + "4. ID / Passport Number : "+idno.getText().toString()+"\n"
                       + "5. Amount               : "+fare.getSelectedItem().toString()+"\n"
                       + "6. Payment Mode         : "+mode.getSelectedItem().toString()+"\n";

        //Creating SendMail object
        SendMail sm = new SendMail(this, emailaddress, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

}
