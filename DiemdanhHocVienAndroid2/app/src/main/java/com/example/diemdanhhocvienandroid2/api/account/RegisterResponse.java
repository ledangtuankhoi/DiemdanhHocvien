package com.example.diemdanhhocvienandroid2.api.account;

public class RegisterResponse {
    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private int UserId ;
    private String Username ;
    private String Email ;
}
