package com.example.fastjobs.entity;

public class District {

    private int districtId;
    private String districtName;
    private String districtType;
    private String provinceId;

    public District() {
    }

    public District(int districtId, String districtName, String districtType, String provinceId) {
        this.districtId = districtId;
        this.districtName = districtName;
        this.districtType = districtType;
        this.provinceId = provinceId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictType() {
        return districtType;
    }

    public void setDistrictType(String districtType) {
        this.districtType = districtType;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}
