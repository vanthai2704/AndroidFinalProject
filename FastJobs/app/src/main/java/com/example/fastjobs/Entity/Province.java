package com.example.fastjobs.Entity;

public class Province {

     private int provinceId;
     private  String provinceName;
     private String provinceType;

    public Province() {
    }

    public Province(int provinceId, String provinceName, String provinceType) {
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.provinceType = provinceType;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceType() {
        return provinceType;
    }

    public void setProvinceType(String provinceType) {
        this.provinceType = provinceType;
    }
}
