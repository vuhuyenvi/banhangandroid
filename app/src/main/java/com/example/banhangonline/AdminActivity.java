package com.example.banhangonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.banhangonline.Adapter.SanPham_Adapter;
import com.example.banhangonline.Adapter.itemAdmin_Adapter;
import com.example.banhangonline.DAO.SanPham_DAO;
import com.example.banhangonline.DTO.SanPham_DTO;
import com.example.banhangonline.databinding.ActivityAdminBinding;
import com.example.banhangonline.databinding.ActivityLoaiSanPhamBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private RecyclerView recyclerViewAdmin;
    private RecyclerView.Adapter adapterSPAdmin;
    private SanPham_DAO sanPhamDao;
    FloatingActionButton fab;
    ActivityAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fab = findViewById(R.id.fab);
        sanPhamDao = new SanPham_DAO(this);
        recyclerViewAdmin = findViewById(R.id.recyclerViewAdmin);
        initRecyclerView();

        binding.btnBackAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        ArrayList<SanPham_DTO> items = sanPhamDao.getSanPhamByTrangThai(1);
        if (recyclerViewAdmin != null) {
            recyclerViewAdmin.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            adapterSPAdmin = new itemAdmin_Adapter(items);
            recyclerViewAdmin.setAdapter(adapterSPAdmin);
        } else {
            Log.e("HomeFragment", "recyclerViewSP is null");
            Toast.makeText(this, "SanPham RecyclerView is null", Toast.LENGTH_SHORT).show();
        }
    }
}