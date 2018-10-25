package com.example.fastjobs.entity;

public class Commune {

    private int communeId;
    private String communeName;
    private String communeType;
    private String districtId;

    public Commune() {
    }

    public Commune(int communeId, String communeName, String communeType, String districtId) {
        this.communeId = communeId;
        this.communeName = communeName;
        this.communeType = communeType;
        this.districtId = districtId;
    }

    public int getCommuneId() {
        return communeId;
    }

    public void setCommuneId(int communeId) {
        this.communeId = communeId;
    }

    public String getCommuneName() {
        return communeName;
    }

    public void setCommuneName(String communeName) {
        this.communeName = communeName;
    }

    public String getCommuneType() {
        return communeType;
    }

    public void setCommuneType(String communeType) {
        this.communeType = communeType;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }
}
