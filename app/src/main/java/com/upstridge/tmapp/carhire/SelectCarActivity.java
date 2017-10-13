package com.upstridge.tmapp.carhire;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.upstridge.tmapp.R;
import com.upstridge.tmapp.adapters.CustomCarHireAdapter;
import com.upstridge.tmapp.data.CarData;
import com.upstridge.tmapp.decorators.RecyclerDecorator;
import com.upstridge.tmapp.models.Carhire;

import java.util.ArrayList;

import static com.upstridge.tmapp.data.CarData.adapter;
import static com.upstridge.tmapp.data.CarData.rCars;

public class SelectCarActivity extends AppCompatActivity {

    private RecyclerView lv;
    private ArrayList<Carhire> modelArrayList;
    private CustomCarHireAdapter customAdapter;
    private Button btnselect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        final String sdate = bundle.getString("startdate");
        final String stime = bundle.getString("starttime");
        final String edate = bundle.getString("enddate");
        final String etime = bundle.getString("endtime");
        final String location = bundle.getString("location");

        lv = (RecyclerView) findViewById(R.id.carList);

        final GridLayoutManager llm = new GridLayoutManager(this, 1);

        lv.setLayoutManager(llm);



        /*Drawable recDrawable = ContextCompat.getDrawable(this, R.drawable.recycler_spacer);
        RecyclerDecorator recyclerDecorator = new RecyclerDecorator(recDrawable);

        lv.addItemDecoration(recyclerDecorator);*/

        final SearchView searchView = (SearchView)findViewById(R.id.searchBar);

        btnselect = (Button) findViewById(R.id.next);

        final LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error_layout);
        errorLayout.setVisibility(View.GONE);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.load_cars);

        final CarData loan = new CarData(this);
        loan.getCars(sdate, stime, edate, etime, location, lv, errorLayout, progressBar,searchView);

        /*lv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                adapter.notifyDataSetChanged();

                if(llm.findLastCompletelyVisibleItemPosition() == rCars.size()-1){
                    rCars.clear();
                    loan.getCars(sdate, stime, edate, etime, location, lv, errorLayout, progressBar,searchView);
                }

            }
        });*/

        /*this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.refreshEvents(rCars);
            }

        });*/


        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CustomCarHireAdapter.getModel().size() == CustomCarHireAdapter.carCount()){
                    Toast.makeText(SelectCarActivity.this,"Please select atleast one car",Toast.LENGTH_SHORT).show();
                }else {
                    TextView org = (TextView) findViewById(R.id.organization);
                    Intent intent = new Intent(SelectCarActivity.this, NumberOfCarsActivity.class);
                    Bundle b = new Bundle();
                    b.putString("startdate", sdate);
                    b.putString("starttime", stime);
                    b.putString("enddate", edate);
                    b.putString("endtime", etime);
                    b.putString("location", location);
                    b.putString("organization", org.getText().toString());
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });


    }


}
