package com.example.fastjobs.Entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Category {
    private String category_id;
    private String category_name;
    private String category_status;
    private List<Image> images;

    public Category() {
    }

    public Category(String category_name, String category_status, List<Image> images) {
        this.category_name = category_name;
        this.category_status = category_status;
        this.images = images;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_status() {
        return category_status;
    }

    public void setCategory_status(String category_status) {
        this.category_status = category_status;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }


    @Exclude
    @Override
    public String toString() {
        return getCategory_name();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("category_name", category_name);
        result.put("category_status", category_status);
        result.put("images", images);
        return result;
    }
}
