package com.example.fastjobs.entity;
package com.example.fastjobs.Entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
public class User {

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
    private int userId;
    private  String username;
    private  String password;
    private  String userType;
    private  String full_name;
    private  String phone;
    private  String email;
    private  String status;

@IgnoreExtraProperties
public class User {
    private String fullname;
    private String email;
    private String phone;
    private Date dob;
    public User() {
    }

    public User(String fullname, String email, String phone, Date dob) {
        this.fullname = fullname;
        this.email = email;
    public User(int userId, String username, String password, String userType, String full_name, String phone, String email, String status) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.full_name = full_name;
        this.phone = phone;
        this.dob = dob;
        this.email = email;
        this.status = status;
    }

    public User() {
    public int getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getDob() {
        return dob;
    public String getEmail() {
        return email;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fullname", fullname);
        result.put("email", email);
        result.put("phone", phone);
        result.put("dob", dob);
        return result;
    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }
}
