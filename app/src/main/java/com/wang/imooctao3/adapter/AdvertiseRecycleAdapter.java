package com.wang.imooctao3.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wang.imooctao3.GoodsInfoActivity;
import com.wang.imooctao3.R;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class AdvertiseRecycleAdapter extends RecyclerView.Adapter<AdvertiseRecycleAdapter.ViewHolder> {

    private Context context;
    private List<AdvertiseInfo> advertiseInfoList;

    public AdvertiseRecycleAdapter(List<AdvertiseInfo> advertiseInfoList) {
        this.advertiseInfoList = advertiseInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_advertise, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        inflate.setOnClickListener(v -> {
            int goodsId = advertiseInfoList.get(viewHolder.getAdapterPosition()).getGoodsId();
            startGoodsInfoActivity(goodsId);
        });
        return viewHolder;
    }

    private void startGoodsInfoActivity(int goodsId){
        GoodsInfoActivity.startGoodInfoActivity(context, goodsId);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdvertiseInfo advertiseInfo = advertiseInfoList.get(position);
        holder.imageView.setImageDrawable(advertiseInfo.getDrawable());
    }


    @Override
    public int getItemCount() {
        return advertiseInfoList == null ? 0 : advertiseInfoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_advertise);
        }
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class AdvertiseInfo {
        private int goodsId;
        private Drawable drawable;
        private int spanSize;//跨列数
    }
}
