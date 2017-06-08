package com.upstridge.tmapp.sgr;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Wango-PC on 6/8/2017.
 */

public class SgrDownloader extends AsyncTask<String, Integer, String> {
    Context c;
    String address;
    Spinner time;
    Spinner from;
    Spinner to;
    Button search;
    ProgressDialog pd;

    public SgrDownloader(Context c, String address,Spinner time, Spinner from, Spinner to, Button search){
        this.c = c;
        this.address = address;
        this.time = time;
        this.from = from;
        this.to = to;
        this.search = search;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd = new ProgressDialog(c);
        pd.setTitle("Fetch Data");
        pd.setMessage("Fetching Data...Please wait");
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String data = downloadData();
        return data;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        pd.dismiss();
        // Toast.makeText(c, s, Toast.LENGTH_LONG).show();
        if(s != null){
            SgrParser p = new SgrParser(c, s, time, from, to, search);
            p.execute();
        }/*else{
            Toast.makeText(c, "Data is not Available", Toast.LENGTH_SHORT).show();
        }*/
    }

    private String downloadData(){
        InputStream Is = null;
        String line = null;
        String prov = "";




        try{

            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);

            InputStream inputStream = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            //String result = "";

            /*while((line = bufferedReader.readLine()) != null){
                result += line;
            }*/


            //BufferedReader br = new BufferedReader(new InputStreamReader(Is));

            StringBuffer sb = new StringBuffer();

            if(bufferedReader != null){
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line+"\n");
                }
            }else{
                return null;
            }

            bufferedReader.close();
            inputStream.close();
            con.disconnect();


            return sb.toString();

        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(Is != null){
                try {
                    Is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

