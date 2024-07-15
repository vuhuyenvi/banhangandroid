package com.example.banhangonline.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.banhangonline.DTO.GioHang_DTO;
import com.example.banhangonline.DatabaseHelper;

public class GioHang_DAO {
    private DatabaseHelper dbHelper;

    public GioHang_DAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long createOrGetGioHang(int maNguoiDung) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("GioHang", new String[]{"MaGH"}, "MaNguoiDung=?", new String[]{String.valueOf(maNguoiDung)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            long maGH = cursor.getLong(0);
            cursor.close();
            db.close();
            return maGH;
        } else {
            ContentValues values = new ContentValues();
            values.put("MaNguoiDung", maNguoiDung);
            long maGH = db.insert("GioHang", null, values);
            db.close();
            return maGH;
        }
    }

    public double getTongTien(long maGH) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double tongTien = 0;

        Cursor cursor = db.query("GioHang", new String[]{"TongTien"}, "MaGH=?", new String[]{String.valueOf(maGH)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            tongTien = cursor.getDouble(0);
            cursor.close();
        }

        db.close();
        return tongTien;
    }

    public void updateCart(long maGH) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        double tongTien = 0;
        int soLuong = 0;

        Cursor cursor = db.query("ChiTietGioHang", new String[]{"SoLuong", "TongTien"}, "MaGH=?", new String[]{String.valueOf(maGH)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                soLuong += cursor.getInt(0);
                tongTien += cursor.getDouble(1);
            } while (cursor.moveToNext());
            cursor.close();
        }

        ContentValues values = new ContentValues();
        values.put("TongTien", tongTien);
        values.put("SoLuong", soLuong);
        db = dbHelper.getWritableDatabase();
        db.update("GioHang", values, "MaGH=?", new String[]{String.valueOf(maGH)});
        db.close();
    }


}
