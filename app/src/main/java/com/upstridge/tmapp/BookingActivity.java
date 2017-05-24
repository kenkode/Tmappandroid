package com.upstridge.tmapp;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.IdRes;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.BatchUpdateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookingActivity extends Activity {

    String urlAddress = "http://192.168.56.1/tmapp/android/booking.php";
    EditText firstname,lastname,email,phone,idno;
    Spinner fare,mode,seat;
    Button book;
    String vehiclename = "";
    ArrayList<String> seats= new ArrayList<String>();

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
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
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

                final EditText fnametxt = new HideHintEditText(this, "First name");
                fnametxt.setWidth(490);
                fnametxt.setId(R.id.fnametxt);
                fnametxt.setHint("First name");
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_BASELINE,
                        fname.getId());
                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        fname.getId());
                fnametxt.setLayoutParams(param);
                relativeLayout[i].addView(fnametxt);

                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
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

                EditText lnametxt = new HideHintEditText(this, "Last name");
                lnametxt.setWidth(490);
                lnametxt.setId(R.id.lnametxt);
                lnametxt.setHint("Last name");
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_BASELINE,
                        lname.getId());
                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        lname.getId());
                lnametxt.setLayoutParams(param);
                relativeLayout[i].addView(lnametxt);


                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
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

                EditText phonetxt = new HideHintEditText(this, "Phone Number");
                phonetxt.setWidth(490);
                phonetxt.setId(R.id.phonetxt);
                phonetxt.setHint("Phone Number");
                phonetxt.setInputType(InputType.TYPE_CLASS_PHONE);
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_BASELINE,
                        phoneno.getId());
                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        phoneno.getId());
                phonetxt.setLayoutParams(param);
                relativeLayout[i].addView(phonetxt);

                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                // Creating a new TextView
                TextView emailaddress = new TextView(this);
                emailaddress.setId(R.id.emailaddress);
                emailaddress.setText("Email:");
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

                EditText emailtxt = new HideHintEditText(this, "Email Address");
                emailtxt.setWidth(490);
                emailtxt.setId(R.id.emailtxt);
                emailtxt.setHint("Email Address");
                emailtxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_BASELINE,
                        emailaddress.getId());
                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        emailaddress.getId());
                emailtxt.setLayoutParams(param);
                relativeLayout[i].addView(emailtxt);

                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
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

                EditText idtxt = new HideHintEditText(this, "Identity No / Passport No");
                idtxt.setWidth(490);
                idtxt.setId(R.id.idpasstxt);
                idtxt.setHint("Identity No / Passport No");
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_TOP,
                        idnumber.getId());
                idtxt.setLayoutParams(param);
                relativeLayout[i].addView(idtxt);

                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
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
                        490,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                Spinner seattxt = new Spinner(this);
                seattxt.setId(R.id.seattxt);
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_BASELINE,
                        seatno.getId());
                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        seatno.getId());
                seattxt.setLayoutParams(param);
                seattxt.setAdapter(seatArray);
                relativeLayout[i].addView(seattxt);

                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
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
                        490,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                Spinner amounttxt = new Spinner(this);
                amounttxt.setId(R.id.amounttxt);
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_BASELINE,
                        amount.getId());
                param.addRule(RelativeLayout.ALIGN_BOTTOM,
                        amount.getId());
                amounttxt.setLayoutParams(param);
                amounttxt.setAdapter(fareArray);
                relativeLayout[i].addView(amounttxt);

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
                    Sender s = new Sender(BookingActivity.this, urlAddress, organization, vehicle, destination, origin, date, time, arrival, departure, fare, mode, seat, firstname, lastname, email, phone, idno);
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
