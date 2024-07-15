package com.example.banhangonline.DTO;

public class GioHang_DTO {
    private int maGH;
    private double tongTien;
    private int soLuong;
    private int maNguoiDung;

    // Constructors, getters, and setters
    public GioHang_DTO() {}

    public GioHang_DTO(int maGH, double tongTien, int soLuong, int maNguoiDung) {
        this.maGH = maGH;
        this.tongTien = tongTien;
        this.soLuong = soLuong;
        this.maNguoiDung = maNguoiDung;
    }

    public int getMaGH() {
        return maGH;
    }

    public void setMaGH(int maGH) {
        this.maGH = maGH;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }
}
