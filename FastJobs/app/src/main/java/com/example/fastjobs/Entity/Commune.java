package com.example.fastjobs.Entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Commune {

    private String commune_id;
    private String name;
    private String type;
    private String district_id;
    private  String province_id;
    public Commune() {
    }

    public Commune(String commune_id, String name, String type, String district_id, String province_id) {
        this.commune_id = commune_id;
        this.name = name;
        this.type = type;
        this.district_id = district_id;
        this.province_id = province_id;
    }

    public String getCommune_id() {
        return commune_id;
    }

    public void setCommune_id(String commune_id) {
        this.commune_id = commune_id;
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

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("commune_id", commune_id);
        result.put("name", name);
        result.put("type", type);
        result.put("district_id", district_id);
        result.put("province_id", province_id);
        return result;
    }
}
