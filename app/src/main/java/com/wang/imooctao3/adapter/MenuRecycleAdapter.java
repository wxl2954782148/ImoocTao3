package com.wang.imooctao3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.wang.imooctao3.MainActivity;
import com.wang.imooctao3.R;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


public class MenuRecycleAdapter extends RecyclerView.Adapter<MenuRecycleAdapter.ViewHolder> {
    private List<ItemInfo> itemInfoList;
    private Context context;

    public MenuRecycleAdapter(List<ItemInfo> itemInfoList) {
        this.itemInfoList = itemInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_index_menu, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        inflate.setOnClickListener(v -> {
            ItemInfo itemInfo = itemInfoList.get(viewHolder.getAdapterPosition());
            MainActivity.startActivityFromMain(context,itemInfo.cls);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemInfo itemInfo = itemInfoList.get(position);
        holder.itemImg.setImageResource(itemInfo.getImgId());
        holder.itemText.setText(itemInfo.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemInfoList == null ? 0 : itemInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView itemImg;
        private final TextView itemText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.img_menu_item);
            itemText = itemView.findViewById(R.id.text_menu_item);

        }
    }



    @AllArgsConstructor
    @Getter
    @Setter
    public static class ItemInfo {
        private int imgId;
        private String title;
        //点击后跳转的Activity的全限定名
        private String cls;
    }
}
