package com.example.banhangonline;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhangonline.Adapter.GioHang_Adapter;
import com.example.banhangonline.DAO.ChiTietGioHang_DAO;
import com.example.banhangonline.DAO.GioHang_DAO;
import com.example.banhangonline.DAO.SanPham_DAO;
import com.example.banhangonline.DTO.ChiTietGioHang_DTO;
import com.example.banhangonline.DTO.SanPham_DTO;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangFragment extends Fragment {

    private RecyclerView recyclerViewGioHang;
    private GioHang_Adapter gioHangAdapter;
    private SanPham_DAO sanPhamDao;
    private ChiTietGioHang_DAO chiTietGioHangDao;
    private SharedPreferences sharedPreferences;
    private boolean isLoggedIn;
    private TextView txtTongThanhTien;
    private DecimalFormat format;

    private GioHang_DAO gioHangDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        this.format = new DecimalFormat("###,###,###,###");
        recyclerViewGioHang = view.findViewById(R.id.recyclerGioHang);
        txtTongThanhTien = view.findViewById(R.id.txtTongTien);
        sanPhamDao = new SanPham_DAO(getContext());
        gioHangDao = new GioHang_DAO(getContext());
        chiTietGioHangDao = new ChiTietGioHang_DAO(getContext());

        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        int MaND;
        if (!isLoggedIn) {
            MaND = 0;
        } else {
            MaND = sharedPreferences.getInt("MaNguoiDung", 0);
        }
        ArrayList<ChiTietGioHang_DTO> items = chiTietGioHangDao.getCartItems(MaND);
        if (recyclerViewGioHang != null) {

            recyclerViewGioHang.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            gioHangAdapter = new GioHang_Adapter(items, sanPhamDao.getAllSanPham(),txtTongThanhTien);
            layTongTien(gioHangDao.createOrGetGioHang(MaND));
            recyclerViewGioHang.setAdapter(gioHangAdapter);

        } else {
            Log.e("GioHangFragment", "recyclerViewGioHang is null");
            Toast.makeText(getContext(), "RecyclerView is null", Toast.LENGTH_SHORT).show();
        }
    }

    private void layTongTien(long maGH) {
        double tongThanhTien = gioHangDao.getTongTien(maGH);

        txtTongThanhTien.setText(format.format(tongThanhTien)+" đ");
    }

}
