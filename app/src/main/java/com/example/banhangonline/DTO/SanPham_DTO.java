package com.example.banhangonline.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SanPham_DTO implements Parcelable {
    private int maSP;
    private String tenSP;
    private double giaCu;
    private double giaMoi;
    private ArrayList<String> anh; // Corrected declaration
    private String moTa;
    private int maLoai;
    private float rating;

    private int trangThai;

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    private ArrayList<String> boSung;

    // Constructor không tham số
    public SanPham_DTO() {
    }

    public ArrayList<String> getBoSung() {
        return boSung;
    }

    public void setBoSung(ArrayList<String> boSung) {
        this.boSung = boSung;
    }

    // Constructor có tham số
    public SanPham_DTO(int maSP, String tenSP, double giaCu, double giaMoi, ArrayList<String> anh, String moTa,int maLoai, float rating,ArrayList<String> boSung, int trangThai) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaCu = giaCu;
        this.giaMoi = giaMoi;
        this.anh = anh;
        this.moTa = moTa;
        this.maLoai = maLoai;
        this.rating = rating;
        this.boSung = boSung;
        this.trangThai = trangThai;
    }

    // Getter và Setter cho các thuộc tính
    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getGiaCu() {
        return giaCu;
    }

    public void setGiaCu(double giaCu) {
        this.giaCu = giaCu;
    }

    public double getGiaMoi() {
        return giaMoi;
    }

    public void setGiaMoi(double giaMoi) {
        this.giaMoi = giaMoi;
    }

    public ArrayList<String> getAnh() {
        return anh;
    }

    public void setAnh(ArrayList<String> anh) {
        this.anh = anh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    // Implementing Parcelable
    protected SanPham_DTO(Parcel in) {
        maSP = in.readInt();
        tenSP = in.readString();
        giaCu = in.readDouble();
        giaMoi = in.readDouble();
        anh = in.createStringArrayList();
        moTa = in.readString();
        maLoai = in.readInt();
        rating = in.readFloat();
        boSung = in.createStringArrayList();
        trangThai = in.readInt();// Ensure boSung is read correctly
    }

    public static final Creator<SanPham_DTO> CREATOR = new Creator<SanPham_DTO>() {
        @Override
        public SanPham_DTO createFromParcel(Parcel in) {
            return new SanPham_DTO(in);
        }

        @Override
        public SanPham_DTO[] newArray(int size) {
            return new SanPham_DTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(maSP);
        dest.writeString(tenSP);
        dest.writeDouble(giaCu);
        dest.writeDouble(giaMoi);
        dest.writeStringList(anh);
        dest.writeString(moTa);
        dest.writeInt(maLoai);
        dest.writeFloat(rating);
        dest.writeStringList(boSung);
        dest.writeInt(trangThai);// Ensure boSung is written correctly
    }
}
