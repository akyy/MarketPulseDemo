package com.mp.interfaces;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("data")
    Call<JsonArray> loadChanges();
}
