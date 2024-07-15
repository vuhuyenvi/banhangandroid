package com.example.banhangonline;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.banhangonline.Adapter.LoaiSP_Adapter;
import com.example.banhangonline.Adapter.SanPham_Adapter;
import com.example.banhangonline.DAO.LoaiSP_DAO;
import com.example.banhangonline.DAO.NguoiDung_DAO;
import com.example.banhangonline.DAO.SanPham_DAO;
import com.example.banhangonline.DTO.LoaiSP_DTO;
import com.example.banhangonline.DTO.NguoiDung_DTO;
import com.example.banhangonline.DTO.SanPham_DTO;

import java.util.ArrayList;


public class SanPhamFragment extends Fragment {
    private RecyclerView recyclerViewSP;
    private RecyclerView.Adapter adapterSP;
    private SanPham_DAO sanPhamDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_san_pham, container, false);
        sanPhamDao = new SanPham_DAO(getContext());
        recyclerViewSP = view.findViewById(R.id.recyclerSanPham);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        ArrayList<SanPham_DTO> items = sanPhamDao.getAllSanPham();
        if (recyclerViewSP != null) {
            recyclerViewSP.setLayoutManager(new GridLayoutManager(getContext(),2));
            adapterSP = new SanPham_Adapter(items);
            recyclerViewSP.setAdapter(adapterSP);
        } else {
            Log.e("HomeFragment", "recyclerViewSP is null");
            Toast.makeText(getContext(), "SanPham RecyclerView is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sanPhamDao.close();
    }
}