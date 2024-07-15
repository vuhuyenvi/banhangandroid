package com.example.banhangonline;

public class TaiKhoan {
    String ten;
    String email;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTendn() {
        return tendn;
    }

    public void setTendn(String tendn) {
        this.tendn = tendn;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    String tendn;

    public TaiKhoan(String ten, String email, String tendn, String matKhau) {
        this.ten = ten;
        this.email = email;
        this.tendn = tendn;
        this.matKhau = matKhau;
    }

    String matKhau;
}
