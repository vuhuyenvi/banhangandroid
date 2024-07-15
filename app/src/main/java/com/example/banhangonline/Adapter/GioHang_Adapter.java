package com.example.banhangonline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.banhangonline.DAO.ChiTietGioHang_DAO;
import com.example.banhangonline.DAO.GioHang_DAO;
import com.example.banhangonline.DTO.ChiTietGioHang_DTO;
import com.example.banhangonline.DTO.SanPham_DTO;
import com.example.banhangonline.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHang_Adapter extends RecyclerView.Adapter<GioHang_Adapter.ViewHolder> {
    private ArrayList<ChiTietGioHang_DTO> ListSPGH;
    private Context context;
    private ArrayList<SanPham_DTO> ListSanPham;
    private DecimalFormat format;

    private ChiTietGioHang_DAO chiTietGioHangDao;
    private GioHang_DAO gioHangDao;

    // Thêm biến để giữ tham chiếu tới TextView tổng tiền
    private TextView txtTongThanhTien;

    public GioHang_Adapter(ArrayList<ChiTietGioHang_DTO> listSPGH, ArrayList<SanPham_DTO> listSanPham, TextView txtTongThanhTien) {
        ListSPGH = listSPGH;
        ListSanPham = listSanPham;
        this.format = new DecimalFormat("###,###,###,###");
        this.txtTongThanhTien = txtTongThanhTien;
    }

    @NonNull
    @Override
    public GioHang_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_gio_hang, parent, false);
        chiTietGioHangDao = new ChiTietGioHang_DAO(context);
        gioHangDao = new GioHang_DAO(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHang_Adapter.ViewHolder holder, int position) {
        ChiTietGioHang_DTO item = ListSPGH.get(position);
        SanPham_DTO product = findProductById(item.getMaSP());
        if (product != null) {
            holder.txtGiaMoi.setText(format.format(product.getGiaMoi()) + " đ");
            holder.txtTenSP.setText(product.getTenSP());
            holder.txtSoLuong.setText(String.valueOf(item.getSoLuong()));
            holder.txtTongTien.setText(format.format(item.getTongTien()) + " đ");

            String imagePath = product.getAnh().get(0);
            if (isDrawableResource(imagePath)) {
                int drawableResId = holder.itemView.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
                Glide.with(context)
                        .load(drawableResId)
                        .transform(new CenterCrop(), new GranularRoundedCorners(40, 40, 40, 40))
                        .into(holder.imageView);
            } else {
                Glide.with(context).load(new File(imagePath)).into(holder.imageView);
            }

            holder.btnThem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateQuantity(holder, item, product, 1);
                }
            });

            holder.btnGiam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateQuantity(holder, item, product, -1);
                }
            });

            holder.btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ListSPGH.remove(item);
                    chiTietGioHangDao.deleteCartItem(item.getMaGH(), item.getMaSP());
                    gioHangDao.updateCart(item.getMaGH());
                    notifyDataSetChanged();
                    updateTongTien();
                }
            });
        }
    }
    private boolean isDrawableResource(String imagePath) {
        int resId = context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
        return resId != 0;
    }
    private SanPham_DTO findProductById(int maSP) {
        for (SanPham_DTO product : ListSanPham) {
            if (product.getMaSP() == maSP) {
                return product;
            }
        }
        return null;
    }

    private void updateQuantity(ViewHolder holder, ChiTietGioHang_DTO item, SanPham_DTO product, int change) {
        int soLuongMoi = item.getSoLuong() + change;
        if (soLuongMoi < 1) return; // prevent negative quantity

        item.setSoLuong(soLuongMoi);
        item.setTongTien(soLuongMoi * product.getGiaMoi());

        holder.txtSoLuong.setText(String.valueOf(soLuongMoi));
        holder.txtTongTien.setText(format.format(item.getTongTien()) + " đ");

        chiTietGioHangDao.updateCartItem(item.getMaGH(), product.getMaSP(), soLuongMoi, soLuongMoi * product.getGiaMoi());
        gioHangDao.updateCart(item.getMaGH());

        // Cập nhật tổng tiền
        updateTongTien();
    }

    private void updateTongTien() {
        double tongTien = 0;
        for (ChiTietGioHang_DTO item : ListSPGH) {
            tongTien += item.getTongTien();
        }
        txtTongThanhTien.setText(format.format(tongTien) + " đ");
    }

    @Override
    public int getItemCount() {
        return ListSPGH.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSoLuong, txtTenSP, txtGiaMoi, txtTongTien, btnThem, btnGiam, btnXoa;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuongSP);
            txtTenSP = itemView.findViewById(R.id.txtTenSP_GioHang);
            txtGiaMoi = itemView.findViewById(R.id.txtGiaMoi_GioHang);
            txtTongTien = itemView.findViewById(R.id.txtTongTien_GioHang);
            imageView = itemView.findViewById(R.id.imgvGioHang);
            btnGiam = itemView.findViewById(R.id.btnGiam);
            btnThem = itemView.findViewById(R.id.btnThem);
            btnXoa = itemView.findViewById(R.id.btnThem);
        }
    }
}
