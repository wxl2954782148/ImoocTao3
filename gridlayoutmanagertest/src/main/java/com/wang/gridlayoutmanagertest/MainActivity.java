package com.wang.gridlayoutmanagertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (position){
                    case 0:
                        return 2;
                    case 1:
                    case 2:
                        return 1;
                    case 3:
                        return 4;
                }
                return 0;
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyRecyclerViewAdapter(getData()));
    }

    private List<Integer> getData() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.main_spread1);
        list.add(R.drawable.main_spread2);
        list.add(R.drawable.main_spread3);
        list.add(R.drawable.main_spread4);
        return list;
    }
}