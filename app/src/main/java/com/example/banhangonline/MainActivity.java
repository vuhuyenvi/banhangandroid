package com.example.banhangonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.banhangonline.Adapter.LoaiSP_Adapter;
import com.example.banhangonline.Adapter.SanPham_Adapter;
import com.example.banhangonline.DTO.LoaiSP_DTO;
import com.example.banhangonline.DTO.SanPham_DTO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Finish MainActivity to prevent going back
        }, SPLASH_DISPLAY_LENGTH);


    }


}