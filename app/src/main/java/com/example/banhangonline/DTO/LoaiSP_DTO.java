package com.example.banhangonline.DTO;

public class LoaiSP_DTO {
    private String tenLoai;
    private int maLoai;
    private String anhLoai;

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getAnhLoai() {
        return anhLoai;
    }

    public void setAnhLoai(String anhLoai) {
        this.anhLoai = anhLoai;
    }

    public LoaiSP_DTO() {
    }

    public LoaiSP_DTO(int maLoai,String tenLoai , String anhLoai) {
        this.tenLoai = tenLoai;
        this.maLoai = maLoai;
        this.anhLoai = anhLoai;
    }
}
