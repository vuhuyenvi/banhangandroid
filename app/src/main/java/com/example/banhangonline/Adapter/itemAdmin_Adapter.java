package com.example.banhangonline.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.banhangonline.DTO.SanPham_DTO;
import com.example.banhangonline.DetailActivity;
import com.example.banhangonline.DetailAdminActivity;
import com.example.banhangonline.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class itemAdmin_Adapter extends RecyclerView.Adapter<itemAdmin_Adapter.ViewHolder>{

    private ArrayList<SanPham_DTO> items;
    private DecimalFormat format;
    private Context context;


    public itemAdmin_Adapter(ArrayList<SanPham_DTO> items) {
        this.items = items;
        this.format = new DecimalFormat("###,###,###,###");
    }


    @NonNull
    @Override
    public itemAdmin_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new itemAdmin_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdmin_Adapter.ViewHolder holder, int position) {
        SanPham_DTO item = items.get(position);
        holder.recTenSP.setText(item.getTenSP());
        holder.recGiaCu.setText(format.format(item.getGiaCu()) + " đ");
        holder.recGiaCu.setPaintFlags(holder.recGiaCu.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.recGiaMoi.setText(format.format(item.getGiaMoi()));

        String imagePath = item.getAnh().get(0);
        if (isDrawableResource(imagePath)) {
            int drawableResId = holder.itemView.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
            Glide.with(context)
                    .load(drawableResId)
                    .transform(new CenterCrop(), new GranularRoundedCorners(40, 40, 40, 40))
                    .into(holder.recImage);
        } else {
            Glide.with(context).load(new File(imagePath)).into(holder.recImage);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailAdminActivity.class);
            intent.putExtra("object", item);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    private boolean isDrawableResource(String imagePath) {
        int resId = context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
        return resId != 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView recImage;
        TextView recTenSP, recGiaCu, recGiaMoi;
        CardView recCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recImage = itemView.findViewById(R.id.recImage);
            recCard = itemView.findViewById(R.id.recCard);
            recTenSP = itemView.findViewById(R.id.recTenSP);
            recGiaCu = itemView.findViewById(R.id.recGiaCu);
            recGiaMoi = itemView.findViewById(R.id.recGiaMoi);
        }
    }
}
