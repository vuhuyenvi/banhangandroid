package com.example.banhangonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.banhangonline.Adapter.BoSung_Adapter;
import com.example.banhangonline.Adapter.Slide_Adapter;
import com.example.banhangonline.DAO.ChiTietGioHang_DAO;
import com.example.banhangonline.DAO.GioHang_DAO;
import com.example.banhangonline.DAO.SanPham_DAO;
import com.example.banhangonline.DTO.SanPham_DTO;
import com.example.banhangonline.DTO.SlideItem;
import com.example.banhangonline.databinding.ActivityDetailAdminBinding;
import com.example.banhangonline.databinding.ActivityDetailBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailAdminActivity extends AppCompatActivity {
    ActivityDetailAdminBinding binding;
    private SanPham_DTO sanPham;
    private SanPham_DAO sanPhamDao;
    private DecimalFormat format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sanPhamDao = new SanPham_DAO(this);
        this.format = new DecimalFormat("###,###,###,###");
        sanPham = getIntent().getParcelableExtra("object");
        getBundles();
        banner();


    }

    private void banner()
    {
        ArrayList<SlideItem> slideItems = new ArrayList<>();
        for(int i = 0; i < sanPham.getAnh().size(); i++)
        {
            slideItems.add(new SlideItem(sanPham.getAnh().get(i)));
        }
        binding.detailViewPageSlide.setAdapter(new Slide_Adapter(slideItems,binding.detailViewPageSlide));
        binding.detailViewPageSlide.setClipToPadding(false);
        binding.detailViewPageSlide.setClipChildren(false);
        binding.detailViewPageSlide.setOffscreenPageLimit(3);
        binding.detailViewPageSlide.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


    }

    private void getBundles() {

        binding.detailTenSP.setText(sanPham.getTenSP());
        binding.detailGiaMoi.setText(format.format(sanPham.getGiaMoi())+" đ");
        binding.detailRatingBar.setRating((float) sanPham.getRating());
        binding.detailRating.setText(sanPham.getRating()+" Rating");
        binding.detailMoTa.setText(sanPham.getMoTa());

        binding.btnBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sanPhamDao.updateTrangThaiSanPham(sanPham.getMaSP(),0);
                Toast.makeText(DetailAdminActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                finish();
            }
        });

        binding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailAdminActivity.this, UpdateActivity.class)
                        .putExtra("TenSP", sanPham.getTenSP())
                        .putExtra("MoTa", sanPham.getMoTa())
                        .putExtra("GiaCu", sanPham.getGiaCu())
                        .putExtra("Anh", sanPham.getAnh())
                        .putExtra("GiaMoi", sanPham.getGiaMoi())
                        .putExtra("BoSung", sanPham.getBoSung())
                        .putExtra("DanhGia", sanPham.getRating())
                        .putExtra("MaLoai", sanPham.getMaLoai())
                        .putExtra("MaSP", sanPham.getMaSP());
                startActivity(intent);
            }
        });
    }
}