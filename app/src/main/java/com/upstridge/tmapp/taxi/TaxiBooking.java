package com.upstridge.tmapp.taxi;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.upstridge.tmapp.HideHintEditText;
import com.upstridge.tmapp.R;
import com.upstridge.tmapp.SendMail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaxiBooking extends Activity {

    String urlAddress = "http://10.0.2.2/tmapp/android/taxibooking.php";
    //String urlAddress = "http://admin.upstridge.co.ke/android/eventbooking.php";
    TextView seaterror [];
    EditText firstname,lastname,email,phone,idno;
    EditText [] fnametxt,lnametxt,emailtxt,phonetxt,idtxt;
    Spinner[] amounttxt;
    Spinner mode;
    Button book;
    String vehiclename = "";
    double amount= 0;
    int slots = 0;
    ArrayList<String> fnamevalues = new ArrayList<>();
    ArrayList<String> lnamevalues = new ArrayList<>();
    ArrayList<String> phonevalues = new ArrayList<>();
    ArrayList<String> emailvalues = new ArrayList<>();
    ArrayList<String> farevalues = new ArrayList<>();
    ArrayList<String> idnovalues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_booking);

        Bundle bundle = getIntent().getExtras();
        final String organization = bundle.getString("organization");
        final String vid = bundle.getString("vid");
        final String economic = bundle.getString("economic");
        final String from = bundle.getString("from");
        final String to = bundle.getString("to");
        vehiclename = bundle.getString("vehicle");
        slots = bundle.getInt("capacity");
        amount =  Double.parseDouble(economic) * 20;

        String[] modes=new String[]{"Mpesa","Airtel Money","VISA"};

        firstname = (EditText)findViewById(R.id.firstname);
        lastname = (EditText)findViewById(R.id.lastname);
        email = (EditText)findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.phone);
        idno = (EditText)findViewById(R.id.idno);
        final String em = email.getText().toString();


        mode=(Spinner) findViewById(R.id.mode);
        ArrayAdapter<String> modeArray= new ArrayAdapter<String>(TaxiBooking.this,android.R.layout.simple_spinner_item, modes);
        modeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mode.setAdapter(modeArray);



        if(slots > 1) {

            RelativeLayout rootLayout = (RelativeLayout)findViewById(R.id.booking);
            RelativeLayout bottomLayout = (RelativeLayout)findViewById(R.id.last);
            RelativeLayout[] relativeLayout = new RelativeLayout[slots-1];
            fnametxt = new EditText[slots-1];
            lnametxt = new EditText[slots-1];
            phonetxt = new EditText[slots-1];
            emailtxt = new EditText[slots-1];
            amounttxt = new Spinner[slots-1];
            idtxt = new EditText[slots-1];
            //RelativeLayout relativeLayout = new RelativeLayout(this);
            LinearLayout lp = new LinearLayout(this);

            for (int i = 0; i < slots-1; i++) {
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
                        RelativeLayout.LayoutParams.MATCH_PARENT,
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
                        RelativeLayout.LayoutParams.MATCH_PARENT,
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
                param.addRule(RelativeLayout.RIGHT_OF,phoneno.getId());
                phonetxt[i].setInputType(InputType.TYPE_CLASS_PHONE);
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
                emailaddress.setText("Email:");
                emailaddress.setTextSize(15);
                param.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                param.addRule(RelativeLayout.BELOW, phoneno.getId());
                param.setMargins(30, 0, 0, 30);
                emailaddress.setLayoutParams(param);
                relativeLayout[i].addView(emailaddress);

                param = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                emailtxt[i] = new HideHintEditText(this, "Email Address");
                //emailtxt[i].setWidth(490);
                emailtxt[i].setId(R.id.emailtxt);
                emailtxt[i].setHint("Email Address");
                param.addRule(RelativeLayout.RIGHT_OF,emailaddress.getId());
                emailtxt[i].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
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
                        RelativeLayout.LayoutParams.MATCH_PARENT,
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
            param1.addRule(RelativeLayout.BELOW, R.id.dynamic+(slots-1));
            bottomLayout.setLayoutParams(param1);
            // Defining the layout parameters of the TextView

        }
        book = (Button)findViewById(R.id.book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fnamevalues.clear();
                lnamevalues.clear();
                emailvalues.clear();
                phonevalues.clear();
                idnovalues.clear();
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
                    for (int i = 0; i < slots-1; i++) {
                        if (fnametxt[i].getText().toString().trim().equals("")) {
                            fnametxt[i].setError("Please insert " + (i + 2) + " person`s first name");
                        }
                    }
                } else if (islnameEdited() == false) {
                    for (int i = 0; i < slots-1; i++) {
                        if (lnametxt[i].getText().toString().trim().equals("")) {
                            lnametxt[i].setError("Please insert " + (i + 2) + " person`s last name");
                        }
                    }
                } else if (isphoneEdited() == false) {
                    for (int i = 0; i < slots-1; i++) {
                        if (phonetxt[i].getText().toString().trim().equals("")) {
                            phonetxt[i].setError("Please insert " + (i + 2) + " person`s phone number");
                        }
                    }
                } else if (isemailEdited() == false) {
                    for (int i = 0; i < slots-1; i++) {
                        if (emailtxt[i].getText().toString().trim().equals("")) {
                            emailtxt[i].setError("Please insert " + (i + 2) + " person`s email");
                        }
                    }
                } else if (isemailValid() == false) {
                    for (int i = 0; i < slots-1; i++) {
                        if (!emailtxt[i].getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                            emailtxt[i].setError("Please insert a valid email address for " + (i + 2) + " person");
                        }
                    }
                } else if (isidnoEdited() == false) {
                    for (int i = 0; i < slots-1; i++) {
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

                    for (int i = 0; i < slots-1; i++) {
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

                    }


                    //Toast.makeText(BookingActivity.this, builder, Toast.LENGTH_LONG).show();


                    Sender s = new Sender(TaxiBooking.this, urlAddress, organization, vid, to, from, mode, slots, amount, fnamevalues, lnamevalues, emailvalues, phonevalues, idnovalues,economic);
                    s.execute();

                }
            }
        });
    }

    public boolean isfnameEdited(){

        for (int i = 0; i < slots-1; i++) {
            if (fnametxt[i].getText().toString().trim().equals("")) {
                return false;
            }
        }
        // we reached this point so all edit texts have been given input
        return true;
    }

    public boolean islnameEdited(){

        for (int i = 0; i < slots-1; i++) {
            if(lnametxt[i].getText().toString().trim().equals("")){
                return false;
            }
        }
        // we reached this point so all edit texts have been given input
        return true;
    }

    public boolean isphoneEdited(){

        for (int i = 0; i < slots-1; i++) {
            if(phonetxt[i].getText().toString().trim().equals("")){
                return false;
            }
        }
        // we reached this point so all edit texts have been given input
        return true;
    }

    public boolean isemailEdited(){

        for (int i = 0; i < slots-1; i++) {
            if(emailtxt[i].getText().toString().trim().equals("")){
                return false;
            }
        }
        // we reached this point so all edit texts have been given input
        return true;
    }

    public boolean isemailValid(){

        for (int i = 0; i < slots-1; i++) {
            if(!emailtxt[i].getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
                return false;
            }
        }
        // we reached this point so all edit texts have been given input
        return true;
    }

    public boolean isidnoEdited(){

        for (int i = 0; i < slots-1; i++) {
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
                + "5. Payment Mode         : "+mode.getSelectedItem().toString()+"\n";

        //Creating SendMail object
        SendMail sm = new SendMail(this, emailaddress, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }

}
