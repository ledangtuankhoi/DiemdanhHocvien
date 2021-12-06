package com.example.diemdanhhocvienandroid2.api;



import com.example.diemdanhhocvienandroid2.models.ClassP;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClassService {
    @GET("Class/Getclasses/")
    Call<List<ClassP>> ClassIndex();

    @GET("ClassTeacher/Getclasses/")
    Call<List<ClassP>> ClassIndexTeacher(@Query("id") int id);

    //detail class
    @GET("Class/Getclass/")
    Call<ClassP> GetClass(@Query("id") int id);
}
