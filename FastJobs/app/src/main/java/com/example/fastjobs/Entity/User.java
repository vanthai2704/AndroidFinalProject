package com.example.fastjobs.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class User {
    private String fullname;
    private String email;
    private String phone;
    private Date dob;
    private List<Image> images;

    public User() {
    }

    public User(String fullname, String email, String phone, Date dob, List<Image> images) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.images = images;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("fullname", fullname);
        result.put("email", email);
        result.put("phone", phone);
        result.put("dob", dob);
        result.put("images", images);
        return result;
    }


}
