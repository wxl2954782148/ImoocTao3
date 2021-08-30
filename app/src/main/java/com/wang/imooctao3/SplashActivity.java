package com.wang.imooctao3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    public final int START_MAIN_ACTIVITY = 1;
    public final int UPDATE_COUNT_DOWN_CODE = 0;
    public final String COUNT_DOWN_KEY = "COUNT_DOWN_KEY";
    private TextView countDownText;
    //当用户手动跳过时，设置为true
    private volatile boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        countDownText = findViewById(R.id.text_count_down);

        countDownTask();
    }

    /**
     * 倒计时计时器线程
     * 当用户没有手动跳过，进行一个5秒的倒计时，每一秒向handler发送一个更新计时显示器消息
     * 当5次计时结束后用户还没有手动跳过，向handler发送一个启动MainActivity的消息
     */
    private void countDownTask() {
        Thread countDownThread = new Thread(() -> {
            for (int countDown = 5; countDown > 0 && !stop; countDown--) {
                Message message = Message.obtain();
                message.what = UPDATE_COUNT_DOWN_CODE;
                Bundle bundle = new Bundle();
                bundle.putInt(COUNT_DOWN_KEY, countDown);
                message.setData(bundle);
                handler.sendMessage(message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!stop)
                handler.sendEmptyMessage(START_MAIN_ACTIVITY);
        });
        countDownThread.start();
    }

    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == START_MAIN_ACTIVITY) {
                MainActivity.startMainActivity(SplashActivity.this);
                finish();
            }
            if (msg.what == UPDATE_COUNT_DOWN_CODE) {
                Bundle data = msg.getData();
                int count = data.getInt(COUNT_DOWN_KEY);
                countDownText.setText("跳过 " + count);
            }
        }
    };


    public void onClick(View view) {
        if (view.getId() == R.id.text_count_down) {
            stop = true;
            MainActivity.startMainActivity(SplashActivity.this);
            finish();
        }
    }
}