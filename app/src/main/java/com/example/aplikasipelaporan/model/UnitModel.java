package com.example.aplikasipelaporan.model;

public class UnitModel {
    private String key;
    private String laporan;
    private String name;
    private String date;
    private String status;

    public UnitModel() {
    }

    public UnitModel(String key, String laporan, String name, String status, String date) {
        this.key = key;
        this.laporan = laporan;
        this.name = name;
        this.status = status;
        this.date = date;
    }

    public UnitModel(String laporan, String name, String status, String date) {
        this.laporan = laporan;
        this.name = name;
        this.status = status;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLaporan() {
        return laporan;
    }

    public void setLaporan(String laporan) {
        this.laporan = laporan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
