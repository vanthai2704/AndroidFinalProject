package com.example.fastjobs.Entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Post {
    private String post_id;
    private String commune_id;
    private String category_id;
    private String user_id;
    private double price;
    private double remuneration;
    private String location_coordinate;
    private String post_title;
    private String post_content;
    private String post_status;
    private Date post_time;
    private List<Image> images;

    public Post() {
    }

    public Post(String commune_id, String category_id, String user_id, double price, double remuneration, String location_coordinate, String post_title, String post_content, String post_status, Date post_time, List<Image> images) {
        this.commune_id = commune_id;
        this.category_id = category_id;
        this.user_id = user_id;
        this.price = price;
        this.remuneration = remuneration;
        this.location_coordinate = location_coordinate;
        this.post_title = post_title;
        this.post_content = post_content;
        this.post_status = post_status;
        this.post_time = post_time;
        this.images = images;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getCommune_id() {
        return commune_id;
    }

    public void setCommune_id(String commune_id) {
        this.commune_id = commune_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRemuneration() {
        return remuneration;
    }

    public void setRemuneration(double remuneration) {
        this.remuneration = remuneration;
    }

    public String getLocation_coordinate() {
        return location_coordinate;
    }

    public void setLocation_coordinate(String location_coordinate) {
        this.location_coordinate = location_coordinate;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    public String getPost_status() {
        return post_status;
    }

    public void setPost_status(String post_status) {
        this.post_status = post_status;
    }

    public Date getPost_time() {
        return post_time;
    }

    public void setPost_time(Date post_time) {
        this.post_time = post_time;
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
        return getPost_title();
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("commune_id", commune_id);
        result.put("category_id", category_id);
        result.put("user_id", user_id);
        result.put("price", price);
        result.put("remuneration", remuneration);
        result.put("location_coordinate", location_coordinate);
        result.put("post_title", post_title);
        result.put("post_content", post_content);
        result.put("post_status", post_status);
        result.put("post_time", post_time);
        result.put("images", images);
        return result;
    }
}
