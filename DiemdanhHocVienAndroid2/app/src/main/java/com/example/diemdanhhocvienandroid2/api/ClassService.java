package com.example.diemdanhhocvienandroid2.api;



import com.example.diemdanhhocvienandroid2.models.ClassP;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClassService {
    @GET("Class/")
    Call<List<ClassP>> ClassIndex();

//    @POST("register/")
//    Call<RegisterResponse> RegisterUser(@Body RegisterRequest registerRequest);
}
