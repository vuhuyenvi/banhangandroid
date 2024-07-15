package com.example.banhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.banhangonline.DAO.NguoiDung_DAO;
import com.example.banhangonline.DTO.NguoiDung_DTO;

public class LoginActivity extends AppCompatActivity {
    EditText loginUserName, loginPassword;
    Button loginButton;
    TextView loginDK;

    NguoiDung_DAO nguoiDungDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nguoiDungDAO = new NguoiDung_DAO(this);
        loginUserName = findViewById(R.id.login_usename);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        loginDK = findViewById(R.id.loginDK);

        loginDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = loginUserName.getText().toString();
                String matKhau = loginPassword.getText().toString();

                if (userName.isEmpty() || matKhau.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isValid = nguoiDungDAO.checkLogin(userName, matKhau);
                    if (isValid) {
                        NguoiDung_DTO user = nguoiDungDAO.getUserDetails(userName);

                        // Store user details in SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putInt("MaNguoiDung", user.getMaND());
                        editor.putString("userName", user.getTenNguoiDung());
                        editor.putString("email", user.getEmail());
                        editor.putString("phone", user.getSoDT());
                        editor.apply();

                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void onLoginSuccess() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }

}