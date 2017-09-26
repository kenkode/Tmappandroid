package com.upstridge.tmapp.bus;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SearchView;

import com.upstridge.tmapp.R;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

public class OriginActivity extends Activity {

    String url = BASE_URL + "android/origin.php";
    SearchView searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_origin);
        Bundle bundle = getIntent().getExtras();
        String prov = "";
        String province = bundle.getString("province");

        searchBar = (SearchView)findViewById(R.id.searchBar);

        final ListView lv = (ListView) findViewById(R.id.originList);
        if(province.equals("Nairobi")){
          prov = "is_nairobi";
        }else if(province.equals("Central")){
            prov = "is_central";
        }else if(province.equals("Coast")){
            prov = "is_coast";
        }else if(province.equals("Western")){
            prov = "is_western";
        }else if(province.equals("Nyanza")){
            prov = "is_nyanza";
        }else if(province.equals("Eastern")){
            prov = "is_eastern";
        }else if(province.equals("Rift-Valley")){
            prov = "is_rift";
        }else if(province.equals("North-Eastern")){
            prov = "is_northeastern";
        }
        //Toast.makeText(this,province+prov,Toast.LENGTH_SHORT).show();
        final Downloader d = new Downloader(this,url,lv,province,searchBar);
        d.execute();

    }

}
