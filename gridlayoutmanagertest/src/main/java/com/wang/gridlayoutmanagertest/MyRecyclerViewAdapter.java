package com.wang.gridlayoutmanagertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    List<Integer> imgIds;

    public MyRecyclerViewAdapter(List<Integer> imgIds) {
        this.imgIds = imgIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Integer imgId = imgIds.get(position);
        holder.imageView.setImageResource(imgId);
    }

    @Override
    public int getItemCount() {
        return imgIds == null ? 0 : imgIds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_item);
        }
    }
}
