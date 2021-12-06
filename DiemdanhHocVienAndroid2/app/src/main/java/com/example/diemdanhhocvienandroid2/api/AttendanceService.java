package com.example.diemdanhhocvienandroid2.api;

import com.example.diemdanhhocvienandroid2.api.account.RegisterRequest;
import com.example.diemdanhhocvienandroid2.api.account.RegisterResponse;
import com.example.diemdanhhocvienandroid2.models.AttendanceStudent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AttendanceService {

    //attendance student with list attendance
    @GET("AttendanceStudents/GetstudentsInClass/")
    Call<List<AttendanceStudent>> AttendanceStudent(@Query("id") int id);

    @POST("AttendanceStudents/PostStudent/")
    Call<String> attendanceStudent (@Body List<AttendanceStudent> attendanceStudentList);

}
