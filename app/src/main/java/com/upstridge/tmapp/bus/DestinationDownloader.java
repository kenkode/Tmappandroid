package com.upstridge.tmapp.bus;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
 * Created by Wango-PC on 5/4/2017.
 */

public class DestinationDownloader extends AsyncTask<String, Integer, String> {
    Context c;
    String address;
    ListView lv;
    String province;
    String origin;
    SearchView searchView;
    ProgressDialog pd;

    public DestinationDownloader(Context c, String address, ListView lv, String province, String origin, SearchView searchView){
        this.c = c;
        this.address = address;
        this.lv = lv;
        this.province = province;
        this.origin = origin;
        this.searchView = searchView;
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
            DestinationParser p = new DestinationParser(c, s, lv, province, origin, searchView);
            p.execute();
        }else{
            Toast.makeText(c, "Data is not Available", Toast.LENGTH_SHORT).show();
        }
    }

    private String downloadData(){
        InputStream Is = null;
        String line = null;
        String prov = "";


        if(province.equals("Nairobi")){
            prov = "is_nairobi";
        }else if(province.equals("Central")){
            prov = "is_central";
        }else if(province.equals("Western")){
            prov = "is_western";
        }else if(province.equals("Nyanza")){
            prov = "is_nyanza";
        }else if(province.equals("Eastern")){
            prov = "is_eastern";
        }else if(province.equals("North Eastern")){
            prov = "is_northeastern";
        }else if(province.equals("Coast")){
            prov = "is_coast";
        }else if(province.equals("Riftvalley")){
            prov = "is_rift";
        }

        try{

            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            OutputStream outputStream = con.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("province", "UTF-8")+"="+URLEncoder.encode(prov,"UTF-8");
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

            if(bufferedWriter != null){
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
