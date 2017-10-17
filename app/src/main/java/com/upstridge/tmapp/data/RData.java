package com.upstridge.tmapp.data;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.upstridge.tmapp.adapters.CustomRoomAdapter;
import com.upstridge.tmapp.models.Room;
import com.upstridge.tmapp.retrofit.RetrofitInterface;
import com.upstridge.tmapp.retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by root on 10/17/17.
 */

public class RData {
    private final Context context;
    String date;
    String time;
    String branchid;
    public static CustomRoomAdapter adapter;
    public static ArrayList<Room> rCars = new ArrayList<>();

    public RData(Context context, String date, String time, String branchid) {
        this.context = context;
        this.date = date;
        this.time = time;
        this.branchid = branchid;
    }

    public void getRooms(final String date, final String time, final String branchid, final RecyclerView listView, final LinearLayout errorLinear, final ProgressBar loadPrice, final SearchView searchView) {

        rCars.clear();

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);

        Call<List<Room>> retroCars = retrofitInterface.getRooms(branchid);

        retroCars.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, retrofit2.Response<List<Room>> response) {
                final List<Room> cars = response.body();

                for (Room rCar : cars) {
                    rCars.add(rCar);
                }
                if(rCars.size() <= 0) {
                    errorLinear.setVisibility(View.VISIBLE);
                }else {
                    errorLinear.setVisibility(View.GONE);
                }
                loadPrice.setVisibility(View.GONE);
                adapter = new CustomRoomAdapter(context, rCars, date, time, branchid);

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
                        ArrayList<Room> tempArrayList = new ArrayList<Room>();
                        for(Room c: cars){
                            if (textlength <= c.getName().length()) {
                                if (c.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                                    tempArrayList.add(c);
                                }
                            }
                        }
                        adapter = new CustomRoomAdapter(context, tempArrayList, date, time, branchid);
                        listView.setAdapter(adapter);
                        //adapter.getFilter().filter(newText);
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
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
                        getRooms(date, time, branchid, listView, errorLinear, loadPrice ,searchView);
                    }
                });
                snackbar.show();
            }
        });


    }
}
