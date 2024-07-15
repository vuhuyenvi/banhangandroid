package com.example.banhangonline.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.banhangonline.DatabaseHelper;
import com.example.banhangonline.DTO.LoaiSP_DTO;
import java.util.ArrayList;

public class LoaiSP_DAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public LoaiSP_DAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insertLoaiSP(LoaiSP_DTO loaiSP) {
        ContentValues values = new ContentValues();
        values.put("TenLoai", loaiSP.getTenLoai());
        return db.insert("LoaiSP", null, values);
    }

    public ArrayList<LoaiSP_DTO> getAllLoaiSP() {
        ArrayList<LoaiSP_DTO> loaiSPList = new ArrayList<>();
        Cursor cursor = db.query("LoaiSP", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                LoaiSP_DTO loaiSP = new LoaiSP_DTO();
                loaiSP.setMaLoai(cursor.getInt(0));
                loaiSP.setTenLoai(cursor.getString(1));
                loaiSP.setAnhLoai(cursor.getString(2));
                loaiSPList.add(loaiSP);
            }
            cursor.close();
        }
        return loaiSPList;
    }
}
