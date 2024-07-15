package com.example.banhangonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.banhangonline.DAO.SanPham_DAO;
import com.example.banhangonline.DTO.LoaiSP_DTO;
import com.example.banhangonline.DTO.SanPham_DTO;
import com.example.banhangonline.DetailActivity;
import com.example.banhangonline.LoaiSanPhamActivity;
import com.example.banhangonline.R;
import java.util.ArrayList;

public class LoaiSP_Adapter extends RecyclerView.Adapter<LoaiSP_Adapter.ViewHolder> {
    private ArrayList<LoaiSP_DTO> items;
    private Context context;
    SanPham_DAO sanPhamDao;
    public LoaiSP_Adapter(ArrayList<LoaiSP_DTO> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        sanPhamDao = new SanPham_DAO(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_catelogy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiSP_DTO item = items.get(position);
        holder.txtTenLoai.setText(item.getTenLoai());
        int drawableResId = holder.itemView.getResources().getIdentifier(item.getAnhLoai(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResId).into(holder.imgvAnhLoai);

        holder.itemView.setOnClickListener(v -> {
            ArrayList<SanPham_DTO> sanPhamList = sanPhamDao.getSanPhamByMaLoai(item.getMaLoai());
            Intent intent = new Intent(context, LoaiSanPhamActivity.class);
            intent.putParcelableArrayListExtra("object", sanPhamList);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size(); // Ensure this returns the correct number of items
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenLoai;
        ImageView imgvAnhLoai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            imgvAnhLoai = itemView.findViewById(R.id.imgvAnhLoai);
        }
    }
}
