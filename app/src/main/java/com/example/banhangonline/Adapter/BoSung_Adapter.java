package com.example.banhangonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banhangonline.R;
import com.example.banhangonline.databinding.ViewholderBosungBinding;

import java.util.ArrayList;

public class BoSung_Adapter extends RecyclerView.Adapter<BoSung_Adapter.ViewHolder>{
    ArrayList<String> items;
    Context context;
    int selectedPosition = -1;
    int lastSelectedPosition = -1;

    public BoSung_Adapter(ArrayList<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BoSung_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderBosungBinding binding = ViewholderBosungBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BoSung_Adapter.ViewHolder holder, int position) {
        holder.binding.txtBoSung.setText(items.get(position));
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(lastSelectedPosition);
                notifyItemChanged(selectedPosition);
            }
        });
        if(selectedPosition == holder.getAdapterPosition()){
            holder.binding.boSungLayout.setBackgroundResource(R.drawable.bosung_selected);
            holder.binding.txtBoSung.setTextColor(context.getResources().getColor(R.color.green));
        }else
        {
            holder.binding.boSungLayout.setBackgroundResource(R.drawable.bosung_unselected);
            holder.binding.txtBoSung.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ViewholderBosungBinding binding;

        public ViewHolder(ViewholderBosungBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
