package com.example.fastjobs.Entity;

public class User {

    private int userId;
    private  String username;
    private  String password;
    private  String userType;
    private  String full_name;
    private  String phone;
    private  String email;
    private  String status;

    public User() {
    }

    public User(int userId, String username, String password, String userType, String full_name, String phone, String email, String status) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.full_name = full_name;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
