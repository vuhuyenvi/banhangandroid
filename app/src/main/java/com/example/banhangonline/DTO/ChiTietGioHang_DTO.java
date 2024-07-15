package com.example.banhangonline.DTO;

public class ChiTietGioHang_DTO {
    private int maGH;
    private int maSP;
    private int soLuong;
    private double tongTien;

    // Constructors, getters, and setters
    public ChiTietGioHang_DTO() {}

    public ChiTietGioHang_DTO(int maGH, int maSP, int soLuong, double tongTien) {
        this.maGH = maGH;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public int getMaGH() {
        return maGH;
    }

    public void setMaGH(int maGH) {
        this.maGH = maGH;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}
