package com.example.banhangonline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banhangonline.DAO.NguoiDung_DAO;
import com.example.banhangonline.DTO.NguoiDung_DTO;

public class EditProfileActivity extends AppCompatActivity {
    private EditText editName, editEmail, editPhone, editPassword;
    private Button saveButton;
    private NguoiDung_DAO nguoiDungDao;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile1);

        // Initialize views
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        editPassword = findViewById(R.id.editPassword);
        saveButton = findViewById(R.id.saveButton);

        // Initialize DAO and SharedPreferences
        nguoiDungDao = new NguoiDung_DAO(this);
        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        // Load current user information
        loadUserInfo();

        // Set click listener for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveUserInfo();
                finish();
            }
        });
    }

    private void loadUserInfo() {
        String userName = sharedPreferences.getString("userName", "");
        if (!userName.isEmpty()) {
            NguoiDung_DTO user = nguoiDungDao.getUserDetails(userName);
            if (user != null) {
                editName.setText(user.getTenNguoiDung());
                editEmail.setText(user.getEmail());
                editPhone.setText(user.getSoDT());
                editPassword.setText(user.getMatKhau());
            }
        }
    }

    private void saveUserInfo() {
        String userName = sharedPreferences.getString("userName", "");
        if (!userName.isEmpty()) {
            String newName = editName.getText().toString();
            String newEmail = editEmail.getText().toString();
            String newPhone = editPhone.getText().toString();
            String newPassword = editPassword.getText().toString();

            NguoiDung_DTO updatedUser = new NguoiDung_DTO(
                    0,  // Assume ID is auto-incremented and handled by the database
                    1,  // PhanQuyen, assuming a default value
                    newEmail,
                    newName,
                    newPhone,
                    newPassword
            );

            boolean success = nguoiDungDao.updateUser(userName, updatedUser);
            if (success) {
                Toast.makeText(this, "User information updated successfully", Toast.LENGTH_SHORT).show();

                // Update SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", newName);
                editor.putString("email", newEmail);
                editor.putString("phone", newPhone);
                editor.apply();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("userName", newName);
                resultIntent.putExtra("email", newEmail);
                resultIntent.putExtra("phone", newPhone);
                setResult(RESULT_OK, resultIntent);

                finish();  // Close the activity
            } else {
                Toast.makeText(this, "Failed to update user information", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
