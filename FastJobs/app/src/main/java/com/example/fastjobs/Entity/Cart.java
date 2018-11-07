package com.example.fastjobs.Entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Cart {

    private String post_id;
    private String cart_status;
    private Date added_time;

    public Cart() {
    }

    public Cart(String post_id, String cart_status, Date added_time) {
        this.post_id = post_id;
        this.cart_status = cart_status;
        this.added_time = added_time;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getCart_status() {
        return cart_status;
    }

    public void setCart_status(String cart_status) {
        this.cart_status = cart_status;
    }

    public Date getAdded_time() {
        return added_time;
    }

    public void setAdded_time(Date added_time) {
        this.added_time = added_time;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("post_id", post_id);
        result.put("cart_status", post_id);
        result.put("added_time", post_id);
        return result;
    }
}
