package com.example.diemdanhhocvienandroid2.models;

public class User {
    private int UserId ;
    private String Username ;
    private String FirstName ;
    private String LastName ;
    private String Email ;
    private String Password ;

    public String[] getRoleName() {
        return RoleName;
    }

    public void setRoleName(String[] roleName) {
        RoleName = roleName;
    }

    private String[] RoleName;






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
