package com.example.logisticaliot;

public class ThongTin {
    private String Nhietdo;
    private String Doam;
//    private String Anhsang;
    private String Thoigian;

    public ThongTin(String nhietdo, String doam, String thoigian) {
//            public ThongTin(String nhietdo, String doam, String anhsang, String thoigian) {
        Nhietdo = nhietdo;
        Doam = doam;
//        Anhsang = anhsang;
        Thoigian = thoigian;
    }

    public String getNhietdo() {
        return Nhietdo;
    }

    public String getDoam() {
        return Doam;
    }

//    public String getAnhsang() {
//        return Anhsang;
//    }

    public String getThoigian() {
        return Thoigian;
    }

    public void setNhietdo(String nhietdo) {
        Nhietdo = nhietdo;
    }

    public void setDoam(String doam) {
        Doam = doam;
    }

//    public void setAnhsang(String anhsang) {
//        Anhsang = anhsang;
//    }

    public void setThoigian(String thoigian) {
        Thoigian = thoigian;
    }
}
