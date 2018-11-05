package com.example.fastjobs.Entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Province {
    private  String province_id;
    private  String name;
    private String type;

    public Province() {
    }

    public Province(String province_id, String name, String type) {
        this.province_id = province_id;
        this.name = name;
        this.type = type;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("province_id", province_id);
        result.put("name", name);
        result.put("type", type);
        return result;
    }
}
