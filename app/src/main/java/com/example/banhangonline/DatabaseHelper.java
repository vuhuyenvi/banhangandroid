package com.example.banhangonline;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BanHangOnline.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng SanPham
        db.execSQL("CREATE TABLE SanPham (" +
                "MaSP INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenSP TEXT NOT NULL, " +
                "GiaCu REAL, " +
                "GiaMoi REAL, " +
                "Anh TEXT, " +
                "Anh1 TEXT, " +
                "MoTa TEXT, " +
                "MaLoai INTEGER NOT NULL, " +
                "FOREIGN KEY(MaLoai) REFERENCES LoaiSP(MaLoai));");

        // Tạo bảng LoaiSP
        db.execSQL("CREATE TABLE LoaiSP (" +
                "MaLoai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TenLoai TEXT NOT NULL);");

        // Tạo bảng GioHang
        db.execSQL("CREATE TABLE GioHang (" +
                "MaGH INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TongTien REAL, " +
                "SoLuong INTEGER, " +
                "MaNguoiDung INTEGER NOT NULL, " +
                "FOREIGN KEY(MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung));");

        // Tạo bảng ChiTietGioHang
        db.execSQL("CREATE TABLE ChiTietGioHang (" +
                "MaGH INTEGER NOT NULL, " +
                "SoLuong INTEGER, " +
                "TongTien REAL, " +
                "MaSP INTEGER NOT NULL, " +
                "PRIMARY KEY(MaGH, MaSP), " +
                "FOREIGN KEY(MaGH) REFERENCES GioHang(MaGH), " +
                "FOREIGN KEY(MaSP) REFERENCES SanPham(MaSP));");

        // Tạo bảng NguoiDung
        db.execSQL("CREATE TABLE NguoiDung (" +
                "MaNguoiDung INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PhanQuyen INTEGER, " +
                "email TEXT NOT NULL, " +
                "TenNguoiDung TEXT NOT NULL, " +
                "SoDT TEXT, " +
                "MatKhau TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SanPham");
        db.execSQL("DROP TABLE IF EXISTS LoaiSP");
        db.execSQL("DROP TABLE IF EXISTS GioHang");
        db.execSQL("DROP TABLE IF EXISTS ChiTietGioHang");
        db.execSQL("DROP TABLE IF EXISTS NguoiDung");
        onCreate(db);
    }
}
