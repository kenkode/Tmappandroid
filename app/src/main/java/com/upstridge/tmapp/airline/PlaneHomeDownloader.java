package com.upstridge.tmapp.airline;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Wango-PC on 5/4/2017.
 */

public class PlaneHomeDownloader extends AsyncTask<String, Integer, String> {
    Context c;
    String address;
    Spinner from;
    Spinner to;
    Button search;
    EditText btnpick;
    EditText timepick;
    ProgressDialog pd;

    public PlaneHomeDownloader(Context c, String address, Spinner from, Spinner to, Button search, EditText btnpick, EditText timepick){
        this.c = c;
        this.address = address;
        this.from = from;
        this.to = to;
        this.search = search;
        this.btnpick = btnpick;
        this.timepick = timepick;
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
            PlaneHomeParser p = new PlaneHomeParser(c, s, from, to, search, btnpick, timepick);
            p.execute();
        }else{
            Toast.makeText(c, "Data is not Available", Toast.LENGTH_SHORT).show();
        }
    }

    private String downloadData(){
        InputStream Is = null;
        String line = null;
        String prov = "";




        try{

            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            OutputStream outputStream = con.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("type", "UTF-8")+"="+URLEncoder.encode("Airline","UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
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
