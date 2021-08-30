package com.wang.imooctao3.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.imooctao3.R;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MeRecycleAdapter extends RecyclerView.Adapter<MeRecycleAdapter.ViewHolder> {
    private Context context;
    List<ItemInfo> itemInfoList;

    public MeRecycleAdapter(List<ItemInfo> itemInfoList) {
        this.itemInfoList = itemInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_me, parent, false);
        inflate.setOnClickListener(v ->{

        });
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemInfo itemInfo = itemInfoList.get(position);
        holder.imageView.setImageDrawable(itemInfo.getDrawable());
        holder.textView.setText(itemInfo.getName());
    }

    @Override
    public int getItemCount() {
        return itemInfoList == null ? 0 : itemInfoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_item_icon);
            textView = itemView.findViewById(R.id.text_item_name);
        }
    }
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ItemInfo{
        private String name;
        private Drawable drawable;
        private String cls;
    }
}
