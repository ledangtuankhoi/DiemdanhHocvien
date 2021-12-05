package com.example.diemdanhhocvienandroid2.api;

import com.example.diemdanhhocvienandroid2.models.AttendanceStudent;
import com.example.diemdanhhocvienandroid2.models.Student;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


    public static Retrofit getRetrofit(){

        String link = "http://192.168.1.4:45455/api/";

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(logging).build();



        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(link)
                .client(okHttpClient)
                .build();
        return retrofit;

    }

    public static UserService getService(){
        UserService userService = getRetrofit().create(UserService.class);
        return userService;
    }


    public static ClassService getClassService(){
        ClassService classService = getRetrofit().create(ClassService.class);
        return classService;
    }


    public static StudentService getStudentService(){
        StudentService studentService = getRetrofit().create(StudentService.class);
        return studentService;
    }

    public static AttendanceService getAttendanceService(){
        AttendanceService attendanceService = getRetrofit().create(AttendanceService.class);
        return attendanceService;
    }
}
