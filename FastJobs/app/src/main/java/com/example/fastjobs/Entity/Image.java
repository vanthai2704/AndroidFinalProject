package com.example.fastjobs.Entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Image {
    private String image_id;
    private String status;
    private String post_id;
    private String user_id;
    private String category_id;

    public Image() {
    }

    public Image(String status, String post_id, String user_id, String category_id) {
        this.status = status;
        this.post_id = post_id;
        this.user_id = user_id;
        this.category_id = category_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("image_id", image_id);
        result.put("status", status);
        result.put("post_id", post_id);
        result.put("user_id", user_id);
        result.put("category_id", category_id);
        return result;
    }
}
