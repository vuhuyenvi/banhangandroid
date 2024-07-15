package com.example.banhangonline.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.banhangonline.DTO.SanPham_DTO;
import com.example.banhangonline.DetailActivity;
import com.example.banhangonline.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPham_Adapter extends RecyclerView.Adapter<SanPham_Adapter.ViewHolder> {
    private ArrayList<SanPham_DTO> items;
    private DecimalFormat format;
    private Context context;

    public SanPham_Adapter(ArrayList<SanPham_DTO> items) {
        this.items = items;
        this.format = new DecimalFormat("###,###,###,###");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_pop_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham_DTO item = items.get(position);
        holder.txtTenSP.setText(item.getTenSP());
        holder.txtGiaCu.setText(format.format(item.getGiaCu()) + " đ");
        holder.txtGiaCu.setPaintFlags(holder.txtGiaCu.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.txtGiaMoi.setText(format.format(item.getGiaMoi()));
        holder.ratingBar.setRating(item.getRating());

//        int drawableResId = holder.itemView.getResources().getIdentifier(item.getAnh().get(0), "drawable", context.getPackageName());
//        Glide.with(context).load(drawableResId).transform(new CenterCrop(), new GranularRoundedCorners(40, 40, 40, 40)).into(holder.imgvAnhSP);
        String imagePath = item.getAnh().get(0);
        if (isDrawableResource(imagePath)) {
            int drawableResId = holder.itemView.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
            Glide.with(context)
                    .load(drawableResId)
                    .transform(new CenterCrop(), new GranularRoundedCorners(40, 40, 40, 40))
                    .into(holder.imgvAnhSP);
        } else {
            Glide.with(context).load(new File(imagePath)).into(holder.imgvAnhSP);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", item);
            context.startActivity(intent);
        });
    }
    private boolean isDrawableResource(String imagePath) {
        int resId = context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
        return resId != 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSP, txtGiaCu, txtGiaMoi;
        ImageView imgvAnhSP;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtGiaCu = itemView.findViewById(R.id.txtGiaCu);
            txtGiaMoi = itemView.findViewById(R.id.txtGiaMoi);
            imgvAnhSP = itemView.findViewById(R.id.imgvAnhSP);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
