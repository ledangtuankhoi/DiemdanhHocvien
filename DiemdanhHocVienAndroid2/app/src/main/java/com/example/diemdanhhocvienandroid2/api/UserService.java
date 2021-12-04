package com.example.diemdanhhocvienandroid2.api;

import com.example.diemdanhhocvienandroid2.api.account.LoginRequest;
import com.example.diemdanhhocvienandroid2.api.account.RegisterRequest;
import com.example.diemdanhhocvienandroid2.api.account.RegisterResponse;
import com.example.diemdanhhocvienandroid2.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("Login/login")
    Call<User> loginUser(@Body LoginRequest loginRequest);

    @POST("register/post")
    Call<RegisterResponse> RegisterUser(@Body RegisterRequest registerRequest);
}
