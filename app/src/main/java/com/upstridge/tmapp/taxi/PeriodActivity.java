package com.upstridge.tmapp.taxi;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.upstridge.tmapp.R;

public class PeriodActivity extends Activity {

    String url = "http://10.0.2.2/tmapp/android/destination.php";
    //String url = "http://admin.upstridge.co.ke/android/destination.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period);

        Bundle bundle = getIntent().getExtras();
        //String date = bundle.getString("date");
        String economic = bundle.getString("economic");
        String capacity = bundle.getString("capacity");
        String vehicle = bundle.getString("vehicle");
        String vid = bundle.getString("vid");
        String organization = bundle.getString("organization");

        Spinner from = (Spinner) findViewById(R.id.from);
        Spinner to = (Spinner) findViewById(R.id.to);
        EditText nop = (EditText)findViewById(R.id.nop);
        Button search = (Button) findViewById(R.id.button);


        final PeriodDownloader d = new PeriodDownloader(this, url, from, to, search,economic,nop,vehicle,vid,organization);
        d.execute();
    }

}
