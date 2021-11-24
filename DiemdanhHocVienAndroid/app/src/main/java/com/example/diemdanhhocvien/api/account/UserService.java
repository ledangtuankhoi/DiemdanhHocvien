package com.example.diemdanhhocvien.api.account;

import com.example.diemdanhhocvien.api.account.LoginRequest;
import com.example.diemdanhhocvien.api.account.LoginResponse;
import com.example.diemdanhhocvien.api.account.RegisterRequest;
import com.example.diemdanhhocvien.api.account.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("Login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("register")
    Call<RegisterResponse> RegisterUser(@Body RegisterRequest registerRequest);
}
