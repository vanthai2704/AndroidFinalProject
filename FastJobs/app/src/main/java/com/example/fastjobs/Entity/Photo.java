package com.example.fastjobs.entity;

public class Photo {

    private int photoId;
    private String path;
    private String type;
    private String status;
    private int postId;
    private int userId;

    public Photo() {
    }

    public Photo(int photoId, String path, String type, String status, int postId, int userId) {
        this.photoId = photoId;
        this.path = path;
        this.type = type;
        this.status = status;
        this.postId = postId;
        this.userId = userId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
