package com.example.fastjobs.entity;

public class Post {

     private int postId;
     private String communeId;
     private int categoryId;
     private int userId;
     private double price;
     private double remuneration;
     private String locationCoordinate;
     private String postTitle;
     private String postContent;
     private String postStatus;

    public Post() {
    }

    public Post(int postId, String communeId, int categoryId, int userId, double price, double remuneration, String locationCoordinate, String postTitle, String postContent, String postStatus) {
        this.postId = postId;
        this.communeId = communeId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.price = price;
        this.remuneration = remuneration;
        this.locationCoordinate = locationCoordinate;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postStatus = postStatus;
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

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }
}
