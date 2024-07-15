package com.example.banhangonline.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.banhangonline.DTO.SlideItem;
import com.example.banhangonline.R;

import java.io.File;
import java.util.ArrayList;

public class Slide_Adapter extends RecyclerView.Adapter<Slide_Adapter.SliderViewHolder> {
    private ArrayList<SlideItem> slideItems;
    private ViewPager2 viewPager2;
    private Context context;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            slideItems.addAll(slideItems);
            notifyDataSetChanged();
        }
    };

    public Slide_Adapter(ArrayList<SlideItem> slideItems, ViewPager2 viewPager2) {
        this.slideItems = slideItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public Slide_Adapter.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SliderViewHolder(LayoutInflater.from(context).inflate(R.layout.slide_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Slide_Adapter.SliderViewHolder holder, int position) {
        holder.setImage(slideItems.get(position));
        if(position == slideItems.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide_item);
        }

        void setImage(SlideItem slideItem) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop());

            String imagePath = slideItem.getUrl();
            if (isDrawableResource(imagePath)) {
                int drawableResId = context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
                Glide.with(context)
                        .load(drawableResId)
                        .transform(new CenterCrop(), new GranularRoundedCorners(40, 40, 40, 40))
                        .into(imageView);
            } else {
                Glide.with(context).load(new File(imagePath)).into(imageView);
            }
        }
        private boolean isDrawableResource(String imagePath) {
            int resId = context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
            return resId != 0;
        }
    }
}
