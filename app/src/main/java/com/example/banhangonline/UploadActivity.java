package com.example.banhangonline;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.banhangonline.Adapter.SanPham_Adapter;
import com.example.banhangonline.DAO.LoaiSP_DAO;
import com.example.banhangonline.DAO.SanPham_DAO;
import com.example.banhangonline.DTO.LoaiSP_DTO;
import com.example.banhangonline.DTO.SanPham_DTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadActivity extends AppCompatActivity {

    private ImageView uploadImage;
    private EditText uploadTenSP, uploadMoTa, uploadGiaCu, uploadGiaMoi, uploadBoSung, uploadDanhGia;
    private Spinner spinnerUploadMaLoai;

    private Button uploadButton;
    Uri uri;

    SanPham_DAO sanPhamDao;
    private LoaiSP_DAO loaiSPDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        loaiSPDao = new LoaiSP_DAO(this);
        sanPhamDao = new SanPham_DAO(this);
        // Khởi tạo các thành phần UI
        uploadImage = findViewById(R.id.uploadImage);
        uploadTenSP = findViewById(R.id.uploadTenSP);
        uploadMoTa = findViewById(R.id.uploadMoTa);
        uploadGiaCu = findViewById(R.id.uploadGiaCu);
        uploadGiaMoi = findViewById(R.id.uploadGiaMoi);
        uploadBoSung = findViewById(R.id.uploadBoSung);
        uploadDanhGia = findViewById(R.id.uploadDanhGia);
        spinnerUploadMaLoai = findViewById(R.id.spinnerMaLoai);
        uploadButton = findViewById(R.id.saveButton);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                uri = data.getData();
                                if (uri != null) {
                                    uploadImage.setImageURI(uri);
                                } else {
                                    Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        List<LoaiSP_DTO> loaiSPList = loaiSPDao.getAllLoaiSP();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getTenLoaiSPList(loaiSPList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUploadMaLoai.setAdapter(adapter);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> boSung = new ArrayList<>();
                String[] boSungArray = uploadBoSung.getText().toString().split(",");
                for (String s : boSungArray) {
                    boSung.add(s.trim());
                }
                String tenSP = uploadTenSP.getText().toString();
                String moTa = uploadMoTa.getText().toString();
                double giaCu = Double.parseDouble(uploadGiaCu.getText().toString());
                double giaMoi = Double.parseDouble(uploadGiaMoi.getText().toString());
                float danhGia = Float.parseFloat(uploadDanhGia.getText().toString());
                int maLoai = loaiSPList.get(spinnerUploadMaLoai.getSelectedItemPosition()).getMaLoai();

                if (uri != null) {
                    String imagePath = saveImageToInternalStorage(uri);
                    ArrayList<String> anhMoi = new ArrayList<>();
                    anhMoi.add(imagePath);
                    sanPhamDao.insertSanPham(new SanPham_DTO(0, tenSP, giaCu, giaMoi, anhMoi, moTa, maLoai, danhGia, boSung,1));
                    Toast.makeText(UploadActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UploadActivity.this, AdminActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isDrawableResource(String imagePath) {
        int resId = getResources().getIdentifier(imagePath, "drawable", getPackageName());
        return resId != 0;
    }
    private List<String> getTenLoaiSPList(List<LoaiSP_DTO> loaiSPList) {
        List<String> tenLoaiSPList = new ArrayList<>();
        for (LoaiSP_DTO loaiSP : loaiSPList) {
            tenLoaiSPList.add(loaiSP.getTenLoai());
        }
        return tenLoaiSPList;
    }
    private String saveImageToInternalStorage(Uri uri) {
        try {
            // Use 'getFilesDir()' method to access the internal storage of the app
            File directory = new File(getFilesDir(), "images");
            if (!directory.exists()) {
                directory.mkdir();
            }

            // Create a file to save the image
            File file = new File(directory, System.currentTimeMillis() + ".png");

            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            // Save the bitmap to the file
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();

            // Return the absolute path of the image file
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private String getBoSung(ArrayList<String> boSung)
    {
        String result = "";
        for (int i = 0; i < boSung.size(); i++) {
            result += boSung.get(i);
            if (i < boSung.size() - 1) {
                result += ", ";
            }
        }
        return result;
    }
    private int getLoaiSPPosition(List<LoaiSP_DTO> loaiSPList, int maLoai) {
        for (int i = 0; i < loaiSPList.size(); i++) {
            if (loaiSPList.get(i).getMaLoai() == maLoai) {
                return i;
            }
        }
        return -1;
    }
}