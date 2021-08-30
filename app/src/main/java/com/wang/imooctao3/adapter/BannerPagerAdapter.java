package com.wang.imooctao3.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * 首页轮播图ViewPager适配器
 * 通过Integer.MAX_VALUE实现伪无限轮播
 */
public class BannerPagerAdapter extends PagerAdapter {
    List<ImageView> imageViewList;
    Context context;
    public BannerPagerAdapter(List<ImageView> imageViewList) {
        this.imageViewList = imageViewList;
    }

    @Override
    public int getCount() {
        return imageViewList == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = imageViewList.get(getRealItem(position));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
    public int getRealItem(int position){
        return position % imageViewList.size();
    }
}

