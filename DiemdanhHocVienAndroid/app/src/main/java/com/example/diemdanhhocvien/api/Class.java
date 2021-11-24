package com.example.diemdanhhocvien.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Class extends ApiService {


    //index class
    @GET("api/class")
    Call<Class> index();

    //details class
    @GET("api/class")
    Call<Class> detail(
            @Query("id") int id
    );

}
