package com.upstridge.tmapp.data;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.upstridge.tmapp.adapters.CustomEventAdapter;
import com.upstridge.tmapp.models.Event;
import com.upstridge.tmapp.retrofit.RetrofitInterface;
import com.upstridge.tmapp.retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by root on 10/16/17.
 */

public class EventData {
    private final Context context;
    public static CustomEventAdapter adapter;
    public static ArrayList<Event> rCars = new ArrayList<>();

    public EventData(Context context) {
        this.context = context;
    }

    public void getEvents(final RecyclerView listView, final LinearLayout errorLinear, final ProgressBar loadPrice, final SearchView searchView) {

        rCars.clear();

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);

        Call<List<Event>> retroCars = retrofitInterface.getEvents();

        retroCars.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, retrofit2.Response<List<Event>> response) {
                final List<Event> cars = response.body();

                for (Event rCar : cars) {
                    rCars.add(rCar);
                }
                if(rCars.size() <= 0) {
                    errorLinear.setVisibility(View.VISIBLE);
                }else {
                    errorLinear.setVisibility(View.GONE);
                }
                loadPrice.setVisibility(View.GONE);
                adapter = new CustomEventAdapter(context, rCars);

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
                        ArrayList<Event> tempArrayList = new ArrayList<Event>();
                        for(Event c: cars){
                            if (textlength <= c.getName().length()) {
                                if (c.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                                    tempArrayList.add(c);
                                }
                            }
                        }
                        adapter = new CustomEventAdapter(context, tempArrayList);
                        listView.setAdapter(adapter);
                        //adapter.getFilter().filter(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
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
                        getEvents(listView, errorLinear, loadPrice,  searchView);
                    }
                });
                snackbar.show();
            }
        });


    }
}
