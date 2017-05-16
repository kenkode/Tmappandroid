package com.upstridge.tmapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by Wango-PC on 5/12/2017.
 */

public class Sender extends AsyncTask<Void,Void,String>{

    Context c;
    String urlAddress,organization,vehicle,destination,origin,date,time,arrival,departure;
    EditText firstnametxt,lastnametxt,emailtxt,phonetxt,idnotxt ;
    Spinner fare,mode;
    String firstname,lastname,email,phone,idno,price,paymentmode;

    ProgressDialog pd;

    public Sender(Context c, String urlAddress, String organization, String vehicle,String destination,String origin,String date,String time,String arrival,String departure,Spinner fare, Spinner mode, EditText...editTexts) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.organization = organization;
        this.vehicle = vehicle;
        this.destination = destination;
        this.origin = origin;
        this.date = date;
        this.time = time;
        this.arrival = arrival;
        this.departure = departure;
        this.fare = fare;
        this.mode = mode;

        this.firstnametxt = editTexts[0];
        this.lastnametxt = editTexts[1];
        this.emailtxt = editTexts[2];
        this.phonetxt = editTexts[3];
        this.idnotxt = editTexts[4];

        firstname = firstnametxt.getText().toString();
        lastname = lastnametxt.getText().toString();
        email = emailtxt.getText().toString();
        phone = phonetxt.getText().toString();
        idno = idnotxt.getText().toString();
        price = fare.getSelectedItem().toString();
        paymentmode = mode.getSelectedItem().toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Booking");
        pd.setMessage("Booking...Please Wait");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        return this.send();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pd.dismiss();

        if(s != null){
            Toast.makeText(c,s,Toast.LENGTH_LONG).show();
            firstnametxt.setText("");
            lastnametxt.setText("");
            emailtxt.setText("");
            phonetxt.setText("");
            idnotxt.setText("");

        }else{
            Toast.makeText(c,s,Toast.LENGTH_LONG).show();
        }
    }

    private String send(){
        HttpURLConnection con = Connector.connect(urlAddress);

        if(con == null){
            return null;
        }
        try{
            OutputStream os = con.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bw.write(new DataPackage(firstname,lastname,email,phone,idno,price,paymentmode,organization,vehicle,destination,origin,date,time,arrival,departure).packData());

            bw.flush();
            bw.close();
            os.close();

            int responseCode = con.getResponseCode();

            if(responseCode == con.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response = new StringBuffer();

                String line = null;

                while((line=br.readLine()) != null){
                   response.append(line);
                }

                br.close();

                return response.toString();
            }else{

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
