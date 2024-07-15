package com.example.banhangonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhangonline.DAO.NguoiDung_DAO;
import com.example.banhangonline.DTO.NguoiDung_DTO;

public class SignupActivity extends AppCompatActivity {

    EditText signupUserName, singupEmail, sigupPhone, sigupPassword;
    TextView signupDangNhap;
    Button signupButton;
    NguoiDung_DAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupUserName = findViewById(R.id.signup_username);
        singupEmail = findViewById(R.id.signup_email);
        sigupPhone = findViewById(R.id.signup_phone);
        sigupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        signupDangNhap = findViewById(R.id.signupDN);

        nguoiDungDAO = new NguoiDung_DAO(this);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenNguoiDung = signupUserName.getText().toString();
                String email = singupEmail.getText().toString();
                String soDT = sigupPhone.getText().toString();
                String matKhau = sigupPassword.getText().toString();

                if (tenNguoiDung.isEmpty() || email.isEmpty() || soDT.isEmpty() || matKhau.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    NguoiDung_DTO nguoiDung = new NguoiDung_DTO(1, email, tenNguoiDung, soDT, matKhau);
                    long id = nguoiDungDAO.addUser(nguoiDung);

                    if (id > 0) {
                        Toast.makeText(SignupActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signupDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}