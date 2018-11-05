package com.example.fastjobs.Entity;

import android.net.Uri;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Image {
    private String image_id;
    private String status;
    private String image_type;


    @Exclude
    private Uri image_uri;

    @Exclude
    public Uri getImage_uri() {
        return image_uri;
    }

    @Exclude
    public void setImage_uri(Uri image_uri) {
        this.image_uri = image_uri;
    }

    public Image() {
    }

    public Image(String status, String image_type) {
        this.status = status;
        this.image_type = image_type;
    }
    public Image(String status, String image_type, Uri image_uri) {
        this.status = status;
        this.image_type = image_type;
        this.image_uri = image_uri;
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

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("image_id", image_id);
        result.put("status", status);
        result.put("image_type", image_type);
        return result;
    }
}
