package com.upstridge.tmapp.taxi;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.models.Route;
import com.upstridge.tmapp.retrofit.RetrofitInterface;
import com.upstridge.tmapp.retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.upstridge.tmapp.config.Constants.BASE_URL;

public class PeriodActivity extends Activity {

    String url = BASE_URL + "android/destination.php";
    //String url = "http://admin.upstridge.co.ke/android/destination.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period);

        Bundle bundle = getIntent().getExtras();
        //String date = bundle.getString("date");
        final String economic = bundle.getString("economic");
        final String capacity = bundle.getString("capacity");
        final String vehicle = bundle.getString("vehicle");
        final String vid = bundle.getString("vid");
        final String organization = bundle.getString("organization");

        final Spinner from = (Spinner) findViewById(R.id.from);
        final Spinner to = (Spinner) findViewById(R.id.to);
        final EditText nop = (EditText)findViewById(R.id.nop);
        Button search = (Button) findViewById(R.id.button);

        LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorLayout.setVisibility(View.GONE);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.load_cars);

        getRoutes(from,to,errorLayout,progressBar);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nop.getText().toString().equals("")){
                    nop.setError("Please insert number of people");
                }else {
                    Intent i = new Intent(PeriodActivity.this, TaxiBooking.class);
                    Bundle b = new Bundle();
                    b.putString("vehicle", vehicle);
                    b.putString("economic", economic);
                    b.putInt("capacity", Integer.parseInt(capacity));
                    b.putString("organization", organization);
                    b.putString("vid", vid);
                    b.putString("from", from.getSelectedItem().toString());
                    b.putString("to", to.getSelectedItem().toString());
                    b.putInt("nop", Integer.parseInt(nop.getText().toString()));
                    i.putExtras(b);
                    startActivity(i);
                }
            }
        });

        /*final PeriodDownloader d = new PeriodDownloader(this, url, from, to, search,economic,nop,vehicle,vid,organization);
        d.execute();*/
    }

    public void getRoutes(final Spinner from, final Spinner to, final LinearLayout errorLinear, final ProgressBar loadPrice) {

        final ArrayList<String> rLocations = new ArrayList<>();

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);

        Call<List<Route>> retroRoutes = retrofitInterface.getRoutes("Taxi");

        retroRoutes.enqueue(new Callback<List<Route>>() {
            @Override
            public void onResponse(Call<List<Route>> call, Response<List<Route>> response) {
                final List<Route> route = response.body();
                for (Route rLoc : route) {
                    rLocations.add(rLoc.getName());
                }
                if(rLocations.size() <= 0) {
                    errorLinear.setVisibility(View.VISIBLE);
                }else {
                    errorLinear.setVisibility(View.GONE);
                }
                loadPrice.setVisibility(View.GONE);
                ArrayAdapter adapter = new ArrayAdapter(PeriodActivity.this, android.R.layout.simple_list_item_1, rLocations);
                // apply the Adapter:
                from.setAdapter(adapter);
                to.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Route>> call, Throwable t) {
                t.printStackTrace();
                errorLinear.setVisibility(View.VISIBLE);
                loadPrice.setVisibility(View.GONE);
                t.printStackTrace();
                final Snackbar snackbar = Snackbar.make(from, "Something went wrong!", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                        errorLinear.setVisibility(View.GONE);
                        loadPrice.setVisibility(View.VISIBLE);
                        getRoutes( from, to, errorLinear, loadPrice );
                    }
                });
                snackbar.show();
            }
        });


    }

}
