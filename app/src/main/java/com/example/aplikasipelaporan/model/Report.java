package com.example.aplikasipelaporan.model;

public class Report {
    private String key;
    private String laporan;
    private String name;

    public Report(){

    }

    public Report(String key, String laporan, String name) {
        this.key = key;
        this.laporan = laporan;
        this.name = name;
    }

    public Report(String laporan, String name) {
        this.laporan = laporan;
        this.name = name;
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
}
