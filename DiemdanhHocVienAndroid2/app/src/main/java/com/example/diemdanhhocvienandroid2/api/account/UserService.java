package com.example.diemdanhhocvienandroid2.api.account;

import com.example.diemdanhhocvienandroid2.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("Login/")
    Call<User> loginUser(@Body LoginRequest loginRequest);

    @POST("register/")
    Call<RegisterResponse> RegisterUser(@Body RegisterRequest registerRequest);
}
