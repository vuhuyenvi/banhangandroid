package com.example.banhangonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseHelper dbHelper;
    private DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

//        replaceFragment(new HomeFragment());

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {
                replaceFragment(new HomeFragment());
                navigationView.setCheckedItem(R.id.nav_home);
            } else if (item.getItemId() == R.id.nav_sp) {
                replaceFragment(new SanPhamFragment());
                navigationView.setCheckedItem(R.id.nav_sp);
            } else if (item.getItemId() == R.id.nav_thongbao) {
                replaceFragment(new GioHangFragment());
                navigationView.setCheckedItem(R.id.nav_thongbao);
            } else if (item.getItemId() == R.id.nav_taikhoan) {
                replaceFragment(new TaiKhoanFragment());
                navigationView.setCheckedItem(R.id.nav_taikhoan);
            }else if (item.getItemId() == R.id.nav_admin) {
                Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
                navigationView.setCheckedItem(R.id.nav_admin);
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        } else if (item.getItemId() == R.id.nav_sp) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SanPhamFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_sp);
        } else if (item.getItemId() == R.id.nav_thongbao) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GioHangFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_thongbao);
        } else if (item.getItemId() == R.id.nav_taikhoan) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TaiKhoanFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_taikhoan);
        }else if (item.getItemId() == R.id.nav_admin) {
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
            bottomNavigationView.setSelectedItemId(R.id.nav_admin);
        }
        else if (item.getItemId() == R.id.nav_logout) {
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
            logout();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack(); // Loại bỏ Fragment khỏi ngăn xếp
            } else {
                super.onBackPressed();
            }
        }
    }
    private void logout() {
        // Cập nhật trạng thái đăng xuất trong SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        // Chuyển đến màn hình đăng nhập

    }
}
