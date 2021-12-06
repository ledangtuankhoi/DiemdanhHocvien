package com.example.diemdanhhocvienandroid2.api;

import com.example.diemdanhhocvienandroid2.api.account.LoginRequest;
import com.example.diemdanhhocvienandroid2.api.account.RegisterRequest;
import com.example.diemdanhhocvienandroid2.api.account.RegisterResponse;
import com.example.diemdanhhocvienandroid2.models.AttendanceStudent;
import com.example.diemdanhhocvienandroid2.models.Student;
import com.example.diemdanhhocvienandroid2.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface StudentService {

  //list studetn of class with id is class
   @GET("StudentsInClass/GetstudentsInClass/")
    Call<List<Student>> StudentInClass(@Query("id") int id);


//craete student
    @POST("Student/PostStudent/")
    Call<Student> createstudent(@Body Student s);

//list all student of user
    @GET("Student/Getstudents/")
    Call<List<Student>> Getstudents(@Query("id") int id);

    //delete student
    @DELETE("Student/DeleteStudent/")
    Call<Student> DeleteStudent(@Query("id")int id);

    //edit student
    @PUT("Student/PutStudent/")
    Call<Student> PutStudent(@Query("id")int id,@Body Student student );


    //get student null class
    //id is id of user
    @GET("Student/GetstudentsNullClass/")
    Call<List<Student>> GetstudentsNullClass(@Query("id")int id);

    //deteil student id
    @GET("student/getstudent/")
    Call<Student> GetStudent (@Query("id")int id);
}
