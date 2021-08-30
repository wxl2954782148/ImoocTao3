package com.wang.imooctao3.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.wang.imooctao3.R;
import com.wang.imooctao3.adapter.AdvertiseRecycleAdapter;
import com.wang.imooctao3.adapter.BannerPagerAdapter;
import com.wang.imooctao3.adapter.MenuRecycleAdapter;
import com.wang.imooctao3.adapter.MenuRecycleAdapter.ItemInfo;

import java.util.ArrayList;
import java.util.List;

public class IndexFragment extends Fragment {
    private LinearLayout pointLayout;
    private BannerPagerAdapter bannerPagerAdapter;
    private ViewPager banner_vp;
    private List<ImageView> bannerImgList;
    // 当前轮播图在ViewPager中的位置
    private int currentPosition = 0;
    //当前轮播图在bannerImgList中的位置(视觉位置)
    private int realPosition = 0;
    private SearchView bannerSearchView;
    private RecyclerView menuRecycleView;
    private Context context;
    private RecyclerView advertiseRecycleView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_index, container, false);
        initView(inflate);
        return inflate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

    }

    private void initView(View inflate) {
        //viewPager相关
        banner_vp = inflate.findViewById(R.id.vp_main_banner);
        bannerImgList = getBannerImgList();
        bannerPagerAdapter = new BannerPagerAdapter(bannerImgList);
        banner_vp.setAdapter(bannerPagerAdapter);
        banner_vp.setCurrentItem(currentPosition);
        //轮播图指示器相关
        pointLayout = inflate.findViewById(R.id.linear_point);
        addBannerPoint(pointLayout, bannerImgList.size());
        ImageView firstImg = (ImageView) pointLayout.getChildAt(realPosition);
        firstImg.setImageResource(R.drawable.item_banner_point_focus);
        banner_vp.addOnPageChangeListener(onPageChangeListener);
        SetAutoShuffling();

        bannerSearchView = inflate.findViewById(R.id.edt_search);
        updateSearchViewTextColor(bannerSearchView);

        //主菜单按钮相关
        menuRecycleView = inflate.findViewById(R.id.recycle_menu);
        MenuRecycleAdapter adapter = new MenuRecycleAdapter(getMenuItemInfoList());
        GridLayoutManager menuManager = new GridLayoutManager(context, 4);
        menuRecycleView.setLayoutManager(menuManager);
        menuRecycleView.setAdapter(adapter);

        //广告图片展示相关
        advertiseRecycleView = inflate.findViewById(R.id.recycle_advertise);
        GridLayoutManager advertiseManager = new GridLayoutManager(context, 4);
        advertiseManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return getAdvertiseList().get(position).getSpanSize();
            }
        });
        advertiseRecycleView.setLayoutManager(advertiseManager);
        advertiseRecycleView.setAdapter(new AdvertiseRecycleAdapter(getAdvertiseList()));
    }

    private List<AdvertiseRecycleAdapter.AdvertiseInfo> getAdvertiseList() {
        List<AdvertiseRecycleAdapter.AdvertiseInfo> advertiseInfoList = new ArrayList<>();
        TypedArray typedArray = getResources().obtainTypedArray(R.array.advertise_img);
        int[] goodsIdArr = getResources().getIntArray(R.array.advertise_goods_id);
        int[] spanSizeArr = getResources().getIntArray(R.array.advertise_span_size);
        for (int i = 0; i < typedArray.length(); i++) {
            AdvertiseRecycleAdapter.AdvertiseInfo info = new AdvertiseRecycleAdapter.AdvertiseInfo(
                    goodsIdArr[i],
                    typedArray.getDrawable(i),
                    spanSizeArr[i]
            );
            advertiseInfoList.add(info);
        }
        typedArray.recycle();
        return advertiseInfoList;
    }


    private List<ItemInfo> getMenuItemInfoList() {
        List<ItemInfo> itemInfoList = new ArrayList<>();
        TypedArray typedArray = getResources().obtainTypedArray(R.array.index_menu_item_img);
        String[] titles = getResources().getStringArray(R.array.index_menu_item_title);
        String[] cls = getResources().getStringArray(R.array.index_menu_item_to_activity_cls);
        for (int i = 0; i < typedArray.length(); i++) {
            int imgId = typedArray.getResourceId(i, 0);
            ItemInfo itemInfo = new ItemInfo(imgId, titles[i], cls[i]);
            itemInfoList.add(itemInfo);
        }
        typedArray.recycle();
        return itemInfoList;
    }

    /**
     * 修改SearchView的相关样式
     *
     * @param searchView
     */
    public void updateSearchViewTextColor(SearchView searchView) {
        if (searchView == null) {
            return;
        }
        //搜索框中的输入字体、hint颜色
        Resources resources = searchView.getContext().getResources();
        int textViewId = resources.getIdentifier("android:id/search_src_text", null, null);
        TextView textView = searchView.findViewById(textViewId);
        textView.setTextColor(Color.WHITE);
        textView.setHintTextColor(context.getColor(R.color.transparent_white));

        //去除搜索框的下划线背景
        int plateId = resources.getIdentifier("android:id/search_plate", null, null);
        LinearLayout layout = searchView.findViewById(plateId);
        layout.setBackground(null);
    }

    Handler myHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == AUTO_SHUFFLE_CODE)
                banner_vp.setCurrentItem(++currentPosition);
        }
    };


    private final int AUTO_SHUFFLE_CODE = 1;

    /**
     * 轮播图自动轮播，每过三秒发送一次消息更新轮播图
     */
    public void SetAutoShuffling() {
        Thread shuffleThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                    myHandler.sendEmptyMessage(AUTO_SHUFFLE_CODE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        shuffleThread.start();
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //下一个位置的指示点变成焦点色
            int nextRelPosition = bannerPagerAdapter.getRealItem(position);
            ImageView nextImg = (ImageView) pointLayout.getChildAt(nextRelPosition);
            nextImg.setImageResource(R.drawable.item_banner_point_focus);

            //当前位置指示点变背景色
            ImageView currentImg = (ImageView) pointLayout.getChildAt(realPosition);
            currentImg.setImageResource(R.drawable.item_banner_point_bg);

            currentPosition = position;
            realPosition = nextRelPosition;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * 获取轮播图的ImageView列表
     */
    private List<ImageView> getBannerImgList() {
        List<ImageView> imageViews = new ArrayList<>();
        TypedArray typedArray = getResources().obtainTypedArray(R.array.banner_img_id_array);
        for (int i = 0; i < typedArray.length(); i++) {
            ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_banner, null, false);
            Glide.with(this)
                    .load(typedArray.getResourceId(i, 0))
                    .into(imageView);
            imageViews.add(imageView);
        }
        typedArray.recycle();
        return imageViews;
    }

    /**
     * 添加轮播图指示器
     *
     * @param container 指示器的容器
     * @param count     指示器的个数
     */
    private void addBannerPoint(LinearLayout container, int count) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 4;
        params.rightMargin = 4;

        for (int i = 0; i < count; i++) {
            ImageView view = new ImageView(context);
            view.setImageResource(R.drawable.item_banner_point_bg);
            view.setLayoutParams(params);
            container.addView(view);
        }
    }
}
