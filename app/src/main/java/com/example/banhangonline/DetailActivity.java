package com.example.banhangonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.Adapter.BoSung_Adapter;
import com.example.banhangonline.Adapter.Slide_Adapter;
import com.example.banhangonline.DAO.ChiTietGioHang_DAO;
import com.example.banhangonline.DAO.GioHang_DAO;
import com.example.banhangonline.DTO.SanPham_DTO;
import com.example.banhangonline.DTO.SlideItem;
import com.example.banhangonline.databinding.ActivityDetailBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    private SanPham_DTO sanPham;
    private GioHang_DAO gioHangDAO;
    private ChiTietGioHang_DAO chiTietGioHangDAO;
    private Button btnAddToCart;
    private DecimalFormat format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gioHangDAO = new GioHang_DAO(this);
        chiTietGioHangDAO = new ChiTietGioHang_DAO(this);
        this.format = new DecimalFormat("###,###,###,###");
        getBundles();
        banner();
        initBoSung();

        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                if (!isLoggedIn) {
                    // Người dùng chưa đăng nhập, chuyển hướng tới Activity Đăng nhập
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    addToCart();
                }
            }
        });


    }

    private void initBoSung() {

        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < sanPham.getBoSung().size(); i++)
        {
            list.add(sanPham.getBoSung().get(i));
        }
        binding.recyclerBoSung.setAdapter(new BoSung_Adapter(list));
        binding.recyclerBoSung.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }


    public void addToCart() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int MaNguoiDung = sharedPreferences.getInt("MaNguoiDung", -1);

        if (MaNguoiDung != -1) {
            long maGH = gioHangDAO.createOrGetGioHang(MaNguoiDung);
            int maSP = sanPham.getMaSP();
            int soLuong = 1;
            double tongTien = soLuong * sanPham.getGiaMoi();
            chiTietGioHangDAO.addOrUpdateCartItem((int) maGH, maSP, soLuong, tongTien);
            gioHangDAO.updateCart(maGH);
            Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }
    }
    private void banner()
    {
        ArrayList<SlideItem> slideItems = new ArrayList<>();
        for(int i = 0; i < sanPham.getAnh().size(); i++)
        {
            slideItems.add(new SlideItem(sanPham.getAnh().get(i)));
        }
        binding.viewPageSlide.setAdapter(new Slide_Adapter(slideItems,binding.viewPageSlide));
        binding.viewPageSlide.setClipToPadding(false);
        binding.viewPageSlide.setClipChildren(false);
        binding.viewPageSlide.setOffscreenPageLimit(3);
        binding.viewPageSlide.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private void getBundles() {
        sanPham = getIntent().getParcelableExtra("object");
        binding.txtTenSPDetail.setText(sanPham.getTenSP());
        binding.txtGiaMoiDetail.setText(format.format(sanPham.getGiaMoi())+" đ");
        binding.ratingBar.setRating((float) sanPham.getRating());
        binding.txtRating.setText(sanPham.getRating()+" Rating");
        binding.txtMoTa.setText(sanPham.getMoTa());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}