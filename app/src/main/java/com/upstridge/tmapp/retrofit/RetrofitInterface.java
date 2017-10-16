package com.upstridge.tmapp.retrofit;

/**
 * Created by root on 10/2/17.
 */

import com.upstridge.tmapp.config.Constants;
import com.upstridge.tmapp.models.Aeroplanes;
import com.upstridge.tmapp.models.Booking;
import com.upstridge.tmapp.models.Carhire;
import com.upstridge.tmapp.models.Event;
import com.upstridge.tmapp.models.Hire;
import com.upstridge.tmapp.models.Location;
import com.upstridge.tmapp.models.Route;
import com.upstridge.tmapp.models.Taxi;
import com.upstridge.tmapp.models.Trains;
import com.upstridge.tmapp.models.Vehicles;


import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET(Constants.GET_CARS)
    Call<List<Carhire>> getCars(@Query("start_date") String start_date, @Query("start_time") String start_time,
                          @Query("end_date") String end_date, @Query("end_time") String end_time, @Query("location") String location);

    @GET(Constants.GET_VEHICLES)
    Call<List<Vehicles>> getVehicles(@Query("date") String date, @Query("start_time") String time,
                                 @Query("destination") String destination, @Query("origin") String origin);

    @GET(Constants.GET_TRAINS)
    Call<List<Trains>> getTrains(@Query("date") String date, @Query("start_time") String time,
                                 @Query("destination") String destination, @Query("origin") String origin);

    @GET(Constants.GET_TAXI)
    Call<List<Taxi>> getTaxis();

    @GET(Constants.GET_AIRPLANES)
    Call<List<Aeroplanes>> getAirplanes(@Query("date") String date, @Query("start_time") String time,
                                        @Query("destination") String destination, @Query("origin") String origin);

    @GET(Constants.GET_EVENTS)
    Call<List<Event>> getEvents();

    @GET(Constants.GET_LOCATIONS)
    Call<List<Location>> getLocations();

    @GET(Constants.GET_ROUTES)
    Call<List<Route>> getRoutes(@Query("type") String type);

    @GET(Constants.HIRE_CAR)
    Call<Hire> hireCar(@Query("sdate") String sdate, @Query("stime") String stime,
                       @Query("edate") String edate, @Query("etime") String etime,
                       @Query("location") String location, @Query("organization") String organization,
                       @Query("firstname") String firstname, @Query("lastname") String lastname,
                       @Query("phone") String phone, @Query("email") String email, @Query("idno") String idno,
                       @Query("mode") String mode, @Query("types") String types, @Query("nums") String  nums,
                       @Query("amounts") String amounts, @Query("diffDays") long diffDays, @Query("amount") double amount);


}

