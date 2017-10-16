package com.upstridge.tmapp.events;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.SearchView;
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
 * Created by Wango-PC on 6/8/2017.
 */

public class eventDownloader extends AsyncTask<String, Integer, String> {
    Context c;
    RecyclerView lv;
    String address;
    SearchView search;
    ProgressDialog pd;

    public eventDownloader(Context c, String address, RecyclerView lv, SearchView search){
        this.c = c;
        this.lv = lv;
        this.address = address;
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
            eventParser p = new eventParser(c, s, lv, search);
            p.execute();
        }else{
            Toast.makeText(c, "No event available", Toast.LENGTH_SHORT).show();
        }
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
