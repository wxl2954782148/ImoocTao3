package com.wang.imooctao3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wang.imooctao3.fragment.FindFragment;
import com.wang.imooctao3.fragment.IndexFragment;
import com.wang.imooctao3.fragment.MeFragment;

public class MainActivity extends AppCompatActivity {

    private Button navIndexBtn;
    private Button navFindBtn;
    private Button navMeBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        //导航按钮相关
        navIndexBtn = findViewById(R.id.btn_nav_index);
        navFindBtn = findViewById(R.id.btn_nav_find);
        navMeBtn = findViewById(R.id.btn_nav_me);
        setNavBtnDrawable(navIndexBtn, R.drawable.nav_index_btn_state);
        setNavBtnDrawable(navFindBtn, R.drawable.nav_find_btn_state);
        setNavBtnDrawable(navMeBtn, R.drawable.nav_me_btn_state);
        currentNavBtn = navIndexBtn;
        currentNavBtn.setSelected(true);
        //Fragment相关
        showFragment(new IndexFragment());
    }

    /**
     * 为Button设置一个向上的图标，并设置其图标大小
     *
     * @param button     要设置的按钮
     * @param drawableId 图标id
     */
    private void setNavBtnDrawable(Button button, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(this, drawableId);
        if (drawable != null) {
            drawable.setBounds(0, 0, 100, 100);
        }
        button.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * 临时变量：当前被选中的导航按钮
     */
    private Button currentNavBtn;

    /**
     * 切换按钮的选中状态
     *
     * @param navBtn
     */
    private void setNavBtnSelectState(Button navBtn) {
        if (currentNavBtn == null) {
            navIndexBtn.setSelected(true);
            currentNavBtn = navIndexBtn;
            return;
        }
        if (navBtn != currentNavBtn) {
            navBtn.setSelected(true);
            currentNavBtn.setSelected(false);
            currentNavBtn = navBtn;
        }
    }

    private Fragment currentFragment = new Fragment();

    /**
     * Fragment的切换：隐藏与展示
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        if (fragment == currentFragment)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(R.id.fragment_container_main, fragment);
        }
        transaction
                .hide(currentFragment)
                .show(fragment);
        transaction.commit();
        currentFragment = fragment;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_nav_index:
                setNavBtnSelectState(navIndexBtn);
                showFragment(new IndexFragment());
                break;
            case R.id.btn_nav_find:
                setNavBtnSelectState(navFindBtn);
                showFragment(new FindFragment());
                break;
            case R.id.btn_nav_me:
                setNavBtnSelectState(navMeBtn);
                showFragment(new MeFragment());
                break;
            default:
        }
    }


    /**
     * 从其它Activity启动MainActivity
     *
     * @param context
     */
    public static void startMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    /**
     * 从MainActivity启动一个Activity，通过全限定名的形式
     *
     * @param cls
     */
    public static void startActivityFromMain(Context context, String cls) {
        Intent intent = new Intent();
        ComponentName component = new ComponentName(context.getPackageName(), cls);
        intent.setComponent(component);
        context.startActivity(intent);
    }


}