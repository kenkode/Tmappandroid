package com.upstridge.tmapp.data;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.upstridge.tmapp.adapters.CustomCarHireAdapter;
import com.upstridge.tmapp.adapters.CustomVehicleAdapter;
import com.upstridge.tmapp.models.Carhire;
import com.upstridge.tmapp.models.Vehicles;
import com.upstridge.tmapp.retrofit.RetrofitInterface;
import com.upstridge.tmapp.retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 10/16/17.
 */

public class VehicleData {
    private final Context context;
    public static CustomVehicleAdapter adapter;
    public static ArrayList<Vehicles> rVehicles = new ArrayList<>();
    String date;
    String time;
    String destination;
    String origin;

    public VehicleData(Context context,String date,String time,String destination,String origin) {
        this.context = context;
        this.date = date;
        this.time = time;
        this.destination = destination;
        this.origin = origin;
    }

    public void getVehicles(final String date, final String time, final String destination, final String origin, final RecyclerView listView, final LinearLayout errorLinear, final ProgressBar loadPrice, final SearchView searchView) {
        rVehicles.clear();

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);

        Call<List<Vehicles>> retroCars = retrofitInterface.getVehicles(date,time,destination,origin);

        retroCars.enqueue(new Callback<List<Vehicles>>() {
            @Override
            public void onResponse(Call<List<Vehicles>> call, Response<List<Vehicles>> response) {
                final List<Vehicles> cars = response.body();

                for (Vehicles rCar : cars) {
                    rVehicles.add(rCar);
                }
                if(rVehicles.size() <= 0) {
                    errorLinear.setVisibility(View.VISIBLE);
                }else {
                    errorLinear.setVisibility(View.GONE);
                }
                loadPrice.setVisibility(View.GONE);
                adapter = new CustomVehicleAdapter(context, rVehicles, destination, date, time, origin);

                listView.setAdapter( adapter );

                listView.invalidate();

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        int textlength = newText.length();
                        ArrayList<Vehicles> tempArrayList = new ArrayList<Vehicles>();
                        for(Vehicles c: cars){
                            if (textlength <= c.getType().length()) {
                                if (c.getType().toLowerCase().contains(newText.toString().toLowerCase())) {
                                    tempArrayList.add(c);
                                }
                            }
                        }
                        adapter = new CustomVehicleAdapter(context, tempArrayList, destination, date, time, origin);
                        listView.setAdapter(adapter);
                        //adapter.getFilter().filter(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Vehicles>> call, Throwable t) {
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
                        getVehicles(date, time, destination, origin, listView, errorLinear, loadPrice ,searchView);
                    }
                });
                snackbar.show();
            }
        });


    }

    public void refreshDetails(final CustomCarHireAdapter mAdapter, final ArrayList<Carhire> cars){

    }
}
