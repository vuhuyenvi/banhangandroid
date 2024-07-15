package com.example.banhangonline;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.banhangonline.Adapter.LoaiSP_Adapter;
import com.example.banhangonline.Adapter.SanPham_Adapter;
import com.example.banhangonline.DAO.LoaiSP_DAO;
import com.example.banhangonline.DAO.NguoiDung_DAO;
import com.example.banhangonline.DAO.SanPham_DAO;
import com.example.banhangonline.DTO.LoaiSP_DTO;
import com.example.banhangonline.DTO.NguoiDung_DTO;
import com.example.banhangonline.DTO.SanPham_DTO;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewSP, recyclerViewLoai;
    private RecyclerView.Adapter adapterSP, adapterLoai;
    private SanPham_DAO sanPhamDao;
    private LoaiSP_DAO loaiSPDao;
    SharedPreferences sharedPreferences;
    TextView txtTenNguoiDung_Home, txtXemTatCa;
    NguoiDung_DAO nguoiDungDAO;
    boolean isLoggedIn;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewSP = view.findViewById(R.id.view_pop);
        recyclerViewLoai = view.findViewById(R.id.view_cat);
        txtTenNguoiDung_Home = view.findViewById(R.id.txtTenNguoiDung_Home);
        txtXemTatCa = view.findViewById(R.id.xemqa);
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView);
        navigationView = requireActivity().findViewById(R.id.nav_view);

        sanPhamDao = new SanPham_DAO(getContext());
        loaiSPDao = new LoaiSP_DAO(getContext());
        nguoiDungDAO = new NguoiDung_DAO(getActivity());

        initRecyclerView();

        sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            txtTenNguoiDung_Home.setText("");
        } else {
            String userName = sharedPreferences.getString("userName", "No name");
            NguoiDung_DTO user = nguoiDungDAO.getUserDetails(userName);
            txtTenNguoiDung_Home.setText(user.getTenNguoiDung());
        }

        txtXemTatCa.setOnClickListener(v -> replaceFragment(new SanPhamFragment(), R.id.nav_sp));

        return view;
    }

    private void replaceFragment(Fragment fragment, int navItemId) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        // Update the selected item in the navigation views
        navigationView.setCheckedItem(navItemId);
        bottomNavigationView.setSelectedItemId(navItemId);
    }
    @Override
    public void onResume() {
        super.onResume();initRecyclerView();
    }
    private void initRecyclerView() {
        ArrayList<SanPham_DTO> items = sanPhamDao.getAllSanPham();
        if (recyclerViewSP != null) {
            recyclerViewSP.setLayoutManager(new GridLayoutManager(getContext(), 2));
            adapterSP = new SanPham_Adapter(items);
            recyclerViewSP.setAdapter(adapterSP);
        } else {
            Toast.makeText(getContext(), "SanPham RecyclerView is null", Toast.LENGTH_SHORT).show();
        }

        ArrayList<LoaiSP_DTO> item1 = loaiSPDao.getAllLoaiSP();
        if (recyclerViewLoai != null) {
            recyclerViewLoai.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            adapterLoai = new LoaiSP_Adapter(item1);
            recyclerViewLoai.setAdapter(adapterLoai);
        } else {
            Toast.makeText(getContext(), "LoaiSP RecyclerView is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sanPhamDao.close();
        loaiSPDao.close();
    }
}
