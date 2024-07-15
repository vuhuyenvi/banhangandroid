package com.example.banhangonline.DTO;

public class NguoiDung_DTO {
    private int maND;
    private int phanQuyen;
    private String email;
    private String tenNguoiDung;
    private String soDT;
    private String matKhau;

    public int getMaND() {
        return maND;
    }

    public void setMaND(int maND) {
        this.maND = maND;
    }

    public int getPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(int phanQuyen) {
        this.phanQuyen = phanQuyen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NguoiDung_DTO(int maND, int phanQuyen, String email, String tenNguoiDung, String soDT, String matKhau) {
        this.maND = maND;
        this.phanQuyen = phanQuyen;
        this.email = email;
        this.tenNguoiDung = tenNguoiDung;
        this.soDT = soDT;
        this.matKhau = matKhau;
    }

    public NguoiDung_DTO(int phanQuyen, String email, String tenNguoiDung, String soDT, String matKhau) {
        this.phanQuyen = phanQuyen;
        this.email = email;
        this.tenNguoiDung = tenNguoiDung;
        this.soDT = soDT;
        this.matKhau = matKhau;
    }
}
