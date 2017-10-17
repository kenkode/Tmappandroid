package com.upstridge.tmapp.data;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.upstridge.tmapp.adapters.CustomHotelAdapter;
import com.upstridge.tmapp.models.Hotels;
import com.upstridge.tmapp.retrofit.RetrofitInterface;
import com.upstridge.tmapp.retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by root on 10/17/17.
 */

public class HData {
    private final Context context;
    String date;
    String time;
    String branchid;
    public static CustomHotelAdapter adapter;
    public static ArrayList<Hotels> rCars = new ArrayList<>();

    public HData(Context context, String date, String time, String branchid) {
        this.context = context;
        this.date = date;
        this.time = time;
        this.branchid = branchid;
    }

    public void getHotels(final String date, final String time, final String area, final RecyclerView listView, final LinearLayout errorLinear, final ProgressBar loadPrice, final SearchView searchView) {

        rCars.clear();

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);

        Call<List<Hotels>> retroCars = retrofitInterface.getHotels(date, time, area);

        retroCars.enqueue(new Callback<List<Hotels>>() {
            @Override
            public void onResponse(Call<List<Hotels>> call, retrofit2.Response<List<Hotels>> response) {
                final List<Hotels> cars = response.body();

                for (Hotels rCar : cars) {
                    rCars.add(rCar);
                }
                if(rCars.size() <= 0) {
                    errorLinear.setVisibility(View.VISIBLE);
                }else {
                    errorLinear.setVisibility(View.GONE);
                }
                loadPrice.setVisibility(View.GONE);
                adapter = new CustomHotelAdapter(context, rCars, date, time, area);

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
                        ArrayList<Hotels> tempArrayList = new ArrayList<Hotels>();
                        for(Hotels c: cars){
                            if (textlength <= c.getName().length()) {
                                if (c.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                                    tempArrayList.add(c);
                                }
                            }
                        }
                        adapter = new CustomHotelAdapter(context, tempArrayList, date, time, area);
                        listView.setAdapter(adapter);
                        //adapter.getFilter().filter(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Hotels>> call, Throwable t) {
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
                        getHotels(date, time, area, listView, errorLinear, loadPrice ,searchView);
                    }
                });
                snackbar.show();
            }
        });


    }
}
