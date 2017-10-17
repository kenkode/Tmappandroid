package com.upstridge.tmapp.data;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.upstridge.tmapp.adapters.CustomTaxiAdapter;
import com.upstridge.tmapp.models.Taxi;
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

public class TaxiData {
    private final Context context;
    public static CustomTaxiAdapter adapter;
    public static ArrayList<Taxi> rVehicles = new ArrayList<>();


    public TaxiData(Context context) {
        this.context = context;

    }

    public void getTaxis(final RecyclerView listView, final LinearLayout errorLinear, final ProgressBar loadPrice, final SearchView searchView) {
        rVehicles.clear();

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);

        Call<List<Taxi>> retroCars = retrofitInterface.getTaxis();

        retroCars.enqueue(new Callback<List<Taxi>>() {
            @Override
            public void onResponse(Call<List<Taxi>> call, Response<List<Taxi>> response) {
                final List<Taxi> cars = response.body();

                for (Taxi rCar : cars) {
                    rVehicles.add(rCar);
                }
                if(rVehicles.size() <= 0) {
                    errorLinear.setVisibility(View.VISIBLE);
                }else {
                    errorLinear.setVisibility(View.GONE);
                }
                loadPrice.setVisibility(View.GONE);
                adapter = new CustomTaxiAdapter(context, rVehicles);

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
                        ArrayList<Taxi> tempArrayList = new ArrayList<Taxi>();
                        for(Taxi c: cars){
                            if (textlength <= c.getName().length()) {
                                if (c.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                                    tempArrayList.add(c);
                                }
                            }
                        }
                        adapter = new CustomTaxiAdapter(context, tempArrayList);
                        listView.setAdapter(adapter);
                        //adapter.getFilter().filter(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Taxi>> call, Throwable t) {
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
                        getTaxis(listView, errorLinear, loadPrice ,searchView);
                    }
                });
                snackbar.show();
            }
        });


    }
}
