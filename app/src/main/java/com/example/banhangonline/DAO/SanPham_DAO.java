package com.example.banhangonline.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.banhangonline.DatabaseHelper;
import com.example.banhangonline.DTO.SanPham_DTO;
import java.util.ArrayList;

public class SanPham_DAO {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public SanPham_DAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long insertSanPham(SanPham_DTO sanPham) {
        ContentValues values = new ContentValues();
        values.put("TenSP", sanPham.getTenSP());
        values.put("GiaCu", sanPham.getGiaCu());
        values.put("GiaMoi", sanPham.getGiaMoi());
        values.put("Anh", convertArrayListToString(sanPham.getAnh()));
        values.put("MoTa", sanPham.getMoTa());
        values.put("MaLoai", sanPham.getMaLoai());
        values.put("DanhGia", sanPham.getRating());
        values.put("BoSung",convertArrayListToString(sanPham.getBoSung()));
        values.put("TrangThai", sanPham.getTrangThai());
        return db.insert("SanPham", null, values);
    }

    public ArrayList<SanPham_DTO> getSanPhamByTrangThai(int trangThai) {
        ArrayList<SanPham_DTO> sanPhamList = new ArrayList<>();
        String selection = "TrangThai = ?";
        String[] selectionArgs = { String.valueOf(trangThai) };

        Cursor cursor = db.query("SanPham", null, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                SanPham_DTO sanPham = new SanPham_DTO();
                sanPham.setMaSP(cursor.getInt(0));
                sanPham.setTenSP(cursor.getString(1));
                sanPham.setGiaCu(cursor.getDouble(2));
                sanPham.setGiaMoi(cursor.getDouble(3));
                sanPham.setAnh(convertStringToArrayList(cursor.getString(4)));
                sanPham.setMoTa(cursor.getString(5));
                sanPham.setMaLoai(cursor.getInt(6));
                sanPham.setRating(cursor.getFloat(7));
                sanPham.setBoSung(convertStringToArrayList(cursor.getString(8)));
                sanPham.setTrangThai(cursor.getInt(9));
                sanPhamList.add(sanPham);
            }
            cursor.close();
        }
        return sanPhamList;
    }

    public ArrayList<SanPham_DTO> getAllSanPham() {
        ArrayList<SanPham_DTO> sanPhamList = new ArrayList<>();
        Cursor cursor = db.query("SanPham", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                SanPham_DTO sanPham = new SanPham_DTO();
                sanPham.setMaSP(cursor.getInt(0));
                sanPham.setTenSP(cursor.getString(1));
                sanPham.setGiaCu(cursor.getDouble(2));
                sanPham.setGiaMoi(cursor.getDouble(3));
                sanPham.setAnh(convertStringToArrayList(cursor.getString(4)));
                sanPham.setMoTa(cursor.getString(5));
                sanPham.setMaLoai(cursor.getInt(6));
                sanPham.setRating(cursor.getFloat(7));
                sanPham.setBoSung(convertStringToArrayList(cursor.getString(8)));
                sanPhamList.add(sanPham);
            }
            cursor.close();
        }
        return sanPhamList;
    }
    public ArrayList<SanPham_DTO> getSanPhamByMaLoai(int maLoai) {
        ArrayList<SanPham_DTO> sanPhamList = new ArrayList<>();
        String selection = "MaLoai = ?";
        String[] selectionArgs = { String.valueOf(maLoai) };

        Cursor cursor = db.query("SanPham", null, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                SanPham_DTO sanPham = new SanPham_DTO();
                sanPham.setMaSP(cursor.getInt(0));
                sanPham.setTenSP(cursor.getString(1));
                sanPham.setGiaCu(cursor.getDouble(2));
                sanPham.setGiaMoi(cursor.getDouble(3));
                sanPham.setAnh(convertStringToArrayList(cursor.getString(4)));
                sanPham.setMoTa(cursor.getString(5));
                sanPham.setMaLoai(cursor.getInt(6));
                sanPham.setRating(cursor.getFloat(7));
                sanPham.setBoSung(convertStringToArrayList(cursor.getString(8)));
                sanPhamList.add(sanPham);
            }
            cursor.close();
        }
        return sanPhamList;
    }
    // Helper method to convert ArrayList<String> to a single String
    private String convertArrayListToString(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append(",");
        }
        return sb.toString();
    }

    // Helper method to convert a single String back to ArrayList<String>
    private ArrayList<String> convertStringToArrayList(String str) {
        ArrayList<String> list = new ArrayList<>();
        if (str != null && !str.isEmpty()) {
            String[] items = str.split(",");
            for (String item : items) {
                list.add(item);
            }
        }
        return list;
    }

    public int deleteSanPham(int maSP) {
        String whereClause = "MaSP = ?";
        String[] whereArgs = { String.valueOf(maSP) };
        return db.delete("SanPham", whereClause, whereArgs);
    }

    public int updateTrangThaiSanPham(int maSP, int newTrangThai) {
        ContentValues values = new ContentValues();
        values.put("TrangThai", newTrangThai);

        String whereClause = "MaSP = ?";
        String[] whereArgs = { String.valueOf(maSP) };

        return db.update("SanPham", values, whereClause, whereArgs);
    }

    public int updateSanPham(SanPham_DTO sanPham) {
        ContentValues values = new ContentValues();
        values.put("TenSP", sanPham.getTenSP());
        values.put("GiaCu", sanPham.getGiaCu());
        values.put("GiaMoi", sanPham.getGiaMoi());
        values.put("Anh", convertArrayListToString(sanPham.getAnh()));
        values.put("MoTa", sanPham.getMoTa());
        values.put("MaLoai", sanPham.getMaLoai());
        values.put("DanhGia", sanPham.getRating());
        values.put("BoSung", convertArrayListToString(sanPham.getBoSung()));
        values.put("TrangThai", sanPham.getTrangThai());

        String whereClause = "MaSP = ?";
        String[] whereArgs = { String.valueOf(sanPham.getMaSP()) };

        return db.update("SanPham", values, whereClause, whereArgs);
    }
}
