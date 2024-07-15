package com.example.banhangonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.banhangonline.Adapter.SanPham_Adapter;
import com.example.banhangonline.DTO.SanPham_DTO;
import com.example.banhangonline.databinding.ActivityDetailBinding;
import com.example.banhangonline.databinding.ActivityLoaiSanPhamBinding;

import java.util.ArrayList;

public class LoaiSanPhamActivity extends AppCompatActivity {

    ActivityLoaiSanPhamBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoaiSanPhamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadRecycler();
    }

    private void loadRecycler() {
        ArrayList<SanPham_DTO> items = getIntent().getParcelableArrayListExtra("object");
        binding.recyclerLoaiSanPham.setAdapter(new SanPham_Adapter(items));
        binding.recyclerLoaiSanPham.setLayoutManager(new GridLayoutManager(this,2));

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}