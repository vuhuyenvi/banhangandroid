package com.example.banhangonline.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.banhangonline.DTO.ChiTietGioHang_DTO;
import com.example.banhangonline.DatabaseHelper;

import java.util.ArrayList;

public class ChiTietGioHang_DAO {
    private DatabaseHelper dbHelper;
    private GioHang_DAO gioHangDao;
    public ChiTietGioHang_DAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        gioHangDao = new GioHang_DAO(context);
    }
    public void addOrUpdateCartItem(int maGH, int maSP, int soLuong, double tongTien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("ChiTietGioHang", new String[]{"SoLuong", "TongTien"}, "MaGH=? AND MaSP=?", new String[]{String.valueOf(maGH), String.valueOf(maSP)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int currentSoLuong = cursor.getInt(0);
            double currentTongTien = cursor.getDouble(1);
            soLuong += currentSoLuong;
            tongTien += currentTongTien;

            ContentValues values = new ContentValues();
            values.put("SoLuong", soLuong);
            values.put("TongTien", tongTien);
            db.update("ChiTietGioHang", values, "MaGH=? AND MaSP=?", new String[]{String.valueOf(maGH), String.valueOf(maSP)});
            cursor.close();
        } else {
            ContentValues values = new ContentValues();
            values.put("MaGH", maGH);
            values.put("MaSP", maSP);
            values.put("SoLuong", soLuong);
            values.put("TongTien", tongTien);
            db.insert("ChiTietGioHang", null, values);
        }
        db.close();
    }
    public void updateCartItem(int maGH, int maSP, int soLuong, double tongTien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SoLuong", soLuong);
        values.put("TongTien", tongTien);
        db.update("ChiTietGioHang", values, "MaGH=? AND MaSP=?", new String[]{String.valueOf(maGH), String.valueOf(maSP)});
        db.close();
    }

    public void deleteCartItem(int maGH, int maSP) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("ChiTietGioHang", "MaGH=? AND MaSP=?", new String[]{String.valueOf(maGH), String.valueOf(maSP)});
        db.close();
    }
    public ArrayList<ChiTietGioHang_DTO> getCartItems(int maNguoiDung) {
        ArrayList<ChiTietGioHang_DTO> cartItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Get the cart ID for the user
        long maGH = gioHangDao.createOrGetGioHang(maNguoiDung);

        // Query to get all items in the cart
        Cursor cursor = db.query("ChiTietGioHang", null, "MaGH=?", new String[]{String.valueOf(maGH)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int maSP = cursor.getInt(cursor.getColumnIndexOrThrow("MaSP"));
                int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow("SoLuong"));
                double tongTien = cursor.getDouble(cursor.getColumnIndexOrThrow("TongTien"));
                cartItems.add(new ChiTietGioHang_DTO((int) maGH, maSP, soLuong, tongTien));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return cartItems;
    }
}
