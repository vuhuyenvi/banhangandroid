package com.example.banhangonline.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.banhangonline.DatabaseHelper;
import com.example.banhangonline.DTO.NguoiDung_DTO;

public class NguoiDung_DAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public NguoiDung_DAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addUser(NguoiDung_DTO nguoiDung) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenNguoiDung", nguoiDung.getTenNguoiDung());
        values.put("email", nguoiDung.getEmail());
        values.put("SoDT", nguoiDung.getSoDT());
        values.put("MatKhau", nguoiDung.getMatKhau());
        values.put("PhanQuyen", nguoiDung.getPhanQuyen());

        long id = db.insert("NguoiDung", null, values);
        db.close();
        return id;
    }

    public boolean checkLogin(String TenNguoiDung, String matKhau) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NguoiDung WHERE TenNguoiDung = ? AND MatKhau = ?", new String[]{TenNguoiDung, matKhau});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

    public NguoiDung_DTO getUserDetails(String tenNguoiDung) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NguoiDung WHERE TenNguoiDung = ?", new String[]{tenNguoiDung});
        if (cursor != null) {
            cursor.moveToFirst();
            NguoiDung_DTO user = new NguoiDung_DTO(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
            cursor.close();
            db.close();
            return user;
        }
        db.close();
        return null;
    }
    public boolean updateUser(String oldUserName, NguoiDung_DTO newUser) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenNguoiDung", newUser.getTenNguoiDung());
        values.put("email", newUser.getEmail());
        values.put("SoDT", newUser.getSoDT());
        values.put("MatKhau", newUser.getMatKhau());

        int rowsAffected = db.update("NguoiDung", values, "TenNguoiDung = ?", new String[]{oldUserName});
        db.close();
        return rowsAffected > 0;
    }

}
