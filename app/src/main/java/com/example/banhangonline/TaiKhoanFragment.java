package com.example.banhangonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.banhangonline.DAO.NguoiDung_DAO;
import com.example.banhangonline.DTO.NguoiDung_DTO;

public class TaiKhoanFragment extends Fragment {
    TextView titleName, titleUsername, profileUsername, profileEmail, profilePhone;
    Button btnDangXuat, editButton;

    boolean isLoggedIn;
    SharedPreferences sharedPreferences;
    NguoiDung_DAO nguoiDungDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tai_khoan, container, false);

        titleName = view.findViewById(R.id.titleName);
        titleUsername = view.findViewById(R.id.titleUsername);
        profileUsername = view.findViewById(R.id.profileUserName);
        profileEmail = view.findViewById(R.id.profileEmail);
        profilePhone = view.findViewById(R.id.profilePhone);
        btnDangXuat = view.findViewById(R.id.btnDangXuatprofile);
        editButton = view.findViewById(R.id.editButton);

        sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        nguoiDungDAO = new NguoiDung_DAO(getActivity());
        isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            btnDangXuat.setText("Đăng nhập");
            editButton.setText("Đăng ký");
        } else {
            String userName = sharedPreferences.getString("userName", "No name");
            updateUserInfo(userName);
            editButton.setText("Chỉnh sửa thông tin");
        }

        btnDangXuat.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (isLoggedIn) {
                // Handle logout
                editor.putBoolean("isLoggedIn", false);
                editor.putString("userName", "");
                editor.putString("email", "");
                editor.putString("phone", "");
                editor.apply();

                btnDangXuat.setText("Đăng nhập");
                editButton.setText("Đăng ký");

                // Clear user information from the UI
                titleName.setText("");
                titleUsername.setText("Người dùng");
                profileUsername.setText("No Name");
                profileEmail.setText("No Email");
                profilePhone.setText("No Phone");

                // Update isLoggedIn variable
                isLoggedIn = false;
            } else {
                // Handle login
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        editButton.setOnClickListener(v -> {
            if (!isLoggedIn) {
                // Handle register
                Intent registerIntent = new Intent(getActivity(), SignupActivity.class);
                startActivity(registerIntent);
            } else {
                Intent registerIntent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(registerIntent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isLoggedIn) {
            String userName = sharedPreferences.getString("userName", "No name");
            updateUserInfo(userName);
        }
    }

    private void updateUserInfo(String userName) {
        NguoiDung_DTO user = nguoiDungDAO.getUserDetails(userName);
        if (user != null) {
            titleName.setText(user.getTenNguoiDung());
            titleUsername.setText(user.getTenNguoiDung());
            profileUsername.setText(user.getTenNguoiDung());
            profileEmail.setText(user.getEmail());
            profilePhone.setText(user.getSoDT());
        }
    }
}
