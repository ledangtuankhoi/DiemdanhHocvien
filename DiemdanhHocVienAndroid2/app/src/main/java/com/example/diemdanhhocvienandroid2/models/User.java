package com.example.diemdanhhocvienandroid2.models;

import java.io.Serializable;

public class User implements Serializable {
    private int UserId ;
    private String Username ;
    private String FirstName ;
    private String LastName ;
    private String Email ;
    private String Password ;
    private String[] RoleName;

    public String getRoleName() {
        return RoleName[0].toString().trim();
    }

    public void setRoleName(String[] roleName) {
        RoleName = roleName;
    }







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

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
