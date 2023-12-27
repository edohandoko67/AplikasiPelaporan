package com.example.aplikasipelaporan.model;

public class Report {
    private String key;
    private String laporan;
    private String reaksi;
    private String tanggalMasuk;
    private String status = "";
    private String notif;
    private String YaMengalami; // store as string
    private String YaOrangTau;
    private String YaBantuan;
    private String TidakMengalami;
    private String TidakTahu;
    private String TidakMembantu;
    private String Anak;
    private String Ibu;
    private String AnakIbu;
    private String tanggalUpdate;
    private String hasil;

    public Report(){
    }

    public Report(String key, String laporan, String reaksi, String tanggalMasuk, String status, String notif) {
        this.key = key;
        this.laporan = laporan;
        this.reaksi = reaksi;
        this.tanggalMasuk = tanggalMasuk;
        this.status = status;
        this.notif = notif;
    }

    public Report(String laporan, String reaksi, String tanggalMasuk, String status, String notif) {
        this.laporan = laporan;
        this.reaksi = reaksi;
        this.tanggalMasuk = tanggalMasuk;
        this.status = status;
        this.notif = notif;
    }

    public Report(String status, String tanggalUpdate, String hasil) {
        this.status = status;
        this.tanggalUpdate = tanggalUpdate;
        this.hasil = hasil;
    }

    public String getNotif() {
        return notif;
    }

    public void setNotif(String notif) {
        this.notif = notif;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getReaksi() {
        return reaksi;
    }

    public void setReaksi(String reaksi) {
        this.reaksi = reaksi;
    }

    public String getTanggalMasuk() {
        return tanggalMasuk;
    }

    public void setTanggalMasuk(String tanggalMasuk) {
        this.tanggalMasuk = tanggalMasuk;
    }

    public String getYaMengalami() {
        return YaMengalami;
    }

    public void setYaMengalami(String yaMengalami) {
        YaMengalami = yaMengalami;
    }

    public String getYaOrangTau() {
        return YaOrangTau;
    }

    public void setYaOrangTau(String yaOrangTau) {
        YaOrangTau = yaOrangTau;
    }

    public String getYaBantuan() {
        return YaBantuan;
    }

    public void setYaBantuan(String yaBantuan) {
        YaBantuan = yaBantuan;
    }

    public String getTidakMengalami() {
        return TidakMengalami;
    }

    public void setTidakMengalami(String tidakMengalami) {
        TidakMengalami = tidakMengalami;
    }

    public String getTidakTahu() {
        return TidakTahu;
    }

    public void setTidakTahu(String tidakTahu) {
        TidakTahu = tidakTahu;
    }

    public String getTidakMembantu() {
        return TidakMembantu;
    }

    public void setTidakMembantu(String tidakMembantu) {
        TidakMembantu = tidakMembantu;
    }

    public String getAnak() {
        return Anak;
    }

    public void setAnak(String anak) {
        Anak = anak;
    }

    public String getIbu() {
        return Ibu;
    }

    public void setIbu(String ibu) {
        Ibu = ibu;
    }

    public String getAnakIbu() {
        return AnakIbu;
    }

    public void setAnakIbu(String anakIbu) {
        AnakIbu = anakIbu;
    }

    public String getTanggalUpdate() {
        return tanggalUpdate;
    }

    public void setTanggalUpdate(String tanggalUpdate) {
        this.tanggalUpdate = tanggalUpdate;
    }

    public String getHasil() {
        return hasil;
    }

    public void setHasil(String hasil) {
        this.hasil = hasil;
    }
}
