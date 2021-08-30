package com.wang.imooctao3.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.imooctao3.R;
import com.wang.imooctao3.adapter.MeRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MeFragment extends Fragment {

    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_me, container, false);
        initView(inflate);
        return inflate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    private void initView(View inflate) {
        Button loginBtn = inflate.findViewById(R.id.btn_login);
        Button couponsBtn = inflate.findViewById(R.id.btn_coupons);
        Button discountCardBtn = inflate.findViewById(R.id.btn_discount_card);
        Button shoppingCartBtn = inflate.findViewById(R.id.btn_shopping_cart);
        setButtonDrawables(couponsBtn, R.drawable.me_menu_yh);
        setButtonDrawables(discountCardBtn, R.drawable.me_menu_sail);
        setButtonDrawables(shoppingCartBtn, R.drawable.me_menu_go);

        RecyclerView recyclerView = inflate.findViewById(R.id.me_recycle);
        LinearLayoutManager layout = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(new MeRecycleAdapter(getData()));
    }

    private List<MeRecycleAdapter.ItemInfo> getData() {
        List<MeRecycleAdapter.ItemInfo> infoList = new ArrayList<>();
        TypedArray typedArray = getResources().obtainTypedArray(R.array.me_img);
        String[] names = getResources().getStringArray(R.array.me_name);
        for (int i = 0; i < typedArray.length(); i++) {
            Drawable drawable = typedArray.getDrawable(i);
            MeRecycleAdapter.ItemInfo itemInfo = new MeRecycleAdapter.ItemInfo(names[i], drawable, null);
            infoList.add(itemInfo);
        }
        typedArray.recycle();
        return infoList;
    }

    private void setButtonDrawables(Button button, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable != null) {
            drawable.setBounds(0, 0, 100, 100);
        }
        button.setCompoundDrawables(null, drawable, null, null);
    }
}
