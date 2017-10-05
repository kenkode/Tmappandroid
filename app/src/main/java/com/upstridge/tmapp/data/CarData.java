package com.upstridge.tmapp.data;

/**
 * Created by root on 10/3/17.
 */

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.SearchView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.upstridge.tmapp.adapters.CustomCarHireAdapter;
import com.upstridge.tmapp.models.Carhire;
import com.upstridge.tmapp.retrofit.RetrofitInterface;
import com.upstridge.tmapp.retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class CarData {
    private final Context context;
    CustomCarHireAdapter adapter;

    public CarData(Context context) {
        this.context = context;
    }

    public void getCars(final String startdate, final String starttime, final String enddate, final String endtime, final String location, final ListView listView, final LinearLayout errorLinear, final ProgressBar loadPrice, final SearchView searchView) {

        final ArrayList<Carhire> rCars = new ArrayList<>();

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);

        Call<List<Carhire>> retroCars = retrofitInterface.getCars(startdate,starttime,enddate,endtime,location);

        retroCars.enqueue(new Callback<List<Carhire>>() {
            @Override
            public void onResponse(Call<List<Carhire>> call, retrofit2.Response<List<Carhire>> response) {
                final List<Carhire> cars = response.body();
                for (Carhire rCar : cars) {
                    rCars.add(rCar);
                }
                if(rCars.size() <= 0) {
                    errorLinear.setVisibility(View.VISIBLE);
                }else {
                    errorLinear.setVisibility(View.GONE);
                }
                loadPrice.setVisibility(View.GONE);
                adapter = new CustomCarHireAdapter(context, rCars);
                listView.setAdapter(adapter);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        int textlength = newText.length();
                        ArrayList<Carhire> tempArrayList = new ArrayList<Carhire>();
                        for(Carhire c: cars){
                            if (textlength <= c.getType().length()) {
                                if (c.getType().toLowerCase().contains(newText.toString().toLowerCase())) {
                                    tempArrayList.add(c);
                                }
                            }
                        }
                        adapter = new CustomCarHireAdapter(context, tempArrayList);
                        listView.setAdapter(adapter);
                        //adapter.getFilter().filter(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Carhire>> call, Throwable t) {
                t.printStackTrace();
                errorLinear.setVisibility(View.VISIBLE);
                loadPrice.setVisibility(View.GONE);
                t.printStackTrace();
                final Snackbar snackbar = Snackbar.make(listView, "Something went wrong!", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                        errorLinear.setVisibility(View.GONE);
                        loadPrice.setVisibility(View.VISIBLE);
                        getCars(startdate, starttime, enddate, endtime, location, listView, errorLinear, loadPrice ,searchView);
                    }
                });
                snackbar.show();
            }
        });


    }

}