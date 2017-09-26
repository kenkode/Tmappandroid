package com.upstridge.tmapp.events;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Spinner;
import android.widget.Toast;

import com.upstridge.tmapp.bus.Connector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by Wango-PC on 5/12/2017.
 */

public class Sender extends AsyncTask<Void,Void,String>{

    Context c;
    String urlAddress,organization,eventid;
    //EditText firstnametxt,lastnametxt,emailtxt,phonetxt,idnotxt ;
    ArrayList<String> firstnametxt,lastnametxt,emailtxt,phonetxt,idnotxt,amounttxt;
    Spinner fare,mode;
    String firstname,lastname,email,phone,idno,price,paymentmode,vip, economic, children;
    int slots,adults, child;

    ProgressDialog pd;

    public Sender(Context c, String urlAddress, String organization, String eventid, ArrayList<String>amounttxt, Spinner mode, int slots, String vip, String economic, String children, int adults, int child, ArrayList<String>firstnametxt, ArrayList<String>lastnametxt, ArrayList<String>emailtxt, ArrayList<String>phonetxt, ArrayList<String>idnotxt) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.organization = organization;
        this.eventid = eventid;
        //this.fare = fare;
        this.mode = mode;
        this.slots = slots;
        this.vip = vip;
        this.economic = economic;
        this.children = children;
        this.adults = adults;
        this.child = child;

        this.firstnametxt = firstnametxt;
        this.lastnametxt = lastnametxt;
        this.emailtxt = emailtxt;
        this.phonetxt = phonetxt;
        this.idnotxt = idnotxt;

        /*firstname = firstnametxt.getText().toString();
        lastname = lastnametxt.getText().toString();
        email = emailtxt.getText().toString();
        phone = phonetxt.getText().toString();
        idno = idnotxt.getText().toString();*/
        this.amounttxt = amounttxt;
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
            /*firstnametxt.setText("");
            lastnametxt.setText("");
            emailtxt.setText("");
            phonetxt.setText("");
            idnotxt.setText("");*/

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
            bw.write(new DataPackage(firstnametxt,lastnametxt,emailtxt,phonetxt,idnotxt,amounttxt,paymentmode,organization,eventid,slots, vip, economic, children, adults, child).packData());

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
