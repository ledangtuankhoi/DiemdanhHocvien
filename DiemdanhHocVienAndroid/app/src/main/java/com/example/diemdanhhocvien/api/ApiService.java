package com.example.diemdanhhocvien.api;

import com.example.diemdanhhocvien.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

//    link api
    String link = "http://localhost:57795";
    Gson gson = new GsonBuilder()
            .setDateFormat("dd-MM-yyyy HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(link)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    //login
//    @GET("api")
//    Call<User> Login(
//            @Query("Username") String Username
//            ,@Query("Password") String Password
//            );
//

//    Call<User> login(@Body User user);



}
