package com.example.fastjobs.Entity;

public class Post {

     private int postId;
     private String communeId;
     private int categoryId;
     private int userId;
     private double price;
     private double remuneration;
     private String locationCoordinate;
     private String title;
     private String content;
     private String status;

    public Post() {
    }

    public Post(int postId, String communeId, int categoryId, int userId, double price, double remuneration, String locationCoordinate, String title, String content, String status) {
        this.postId = postId;
        this.communeId = communeId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.price = price;
        this.remuneration = remuneration;
        this.locationCoordinate = locationCoordinate;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getCommuneId() {
        return communeId;
    }

    public void setCommuneId(String communeId) {
        this.communeId = communeId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getLocationCoordinate() {
        return locationCoordinate;
    }

    public void setLocationCoordinate(String locationCoordinate) {
        this.locationCoordinate = locationCoordinate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
