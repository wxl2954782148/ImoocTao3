package com.wang.imooctao3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GoodsInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        Intent intent = getIntent();
        int good_id = intent.getIntExtra("good_id", 0);
        TextView textView = findViewById(R.id.text_goods_info);
        textView.setText(String.valueOf(good_id));
    }


    public static void startGoodInfoActivity(Context context, int goodsId){
        Intent intent = new Intent(context, GoodsInfoActivity.class);
        intent.putExtra("good_id", goodsId);
        context.startActivity(intent);
    }
}