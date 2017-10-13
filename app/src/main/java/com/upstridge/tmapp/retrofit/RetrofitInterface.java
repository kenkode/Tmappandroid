package com.upstridge.tmapp.retrofit;

/**
 * Created by root on 10/2/17.
 */

import com.upstridge.tmapp.config.Constants;
import com.upstridge.tmapp.models.Booking;
import com.upstridge.tmapp.models.Carhire;
import com.upstridge.tmapp.models.Hire;
import com.upstridge.tmapp.models.Location;


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

    @GET(Constants.GET_LOCATIONS)
    Call<List<Location>> getLocations();

    @GET(Constants.HIRE_CAR)
    Call<Hire> hireCar(@Query("sdate") String sdate, @Query("stime") String stime,
                          @Query("edate") String edate, @Query("etime") String etime,
                          @Query("location") String location, @Query("organization") String organization,
                          @Query("firstname") String firstname, @Query("lastname") String lastname,
                          @Query("phone") String phone, @Query("email") String email, @Query("idno") String idno,
                          @Query("mode") String mode, @Query("types") String[] types, @Query("nums") int[] nums,
                          @Query("amounts") double[] amounts, @Query("diffDays") long diffDays, @Query("amount") double amount);


}

