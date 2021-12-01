package com.example.diemdanhhocvienandroid2.api;



import com.example.diemdanhhocvienandroid2.models.ClassP;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClassService {
    @GET("Class/")
    Call<List<ClassP>> ClassIndex();

    @GET("ClassTeacher/")
    Call<List<ClassP>> ClassIndexTeacher(@Query("id") int id);



//    @POST("register/")
//    Call<RegisterResponse> RegisterUser(@Body RegisterRequest registerRequest);
}
