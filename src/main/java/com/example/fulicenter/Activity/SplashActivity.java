package com.example.fulicenter.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.fulicenter.R;
import com.example.fulicenter.utils.MFGT;


/**
 * Created by 涛 on 2016/10/14.
 */
public class SplashActivity extends AppCompatActivity {
    private final long st = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long satrt = System.currentTimeMillis();
//                耗时
                long cos = System.currentTimeMillis() - satrt;
                if (st - cos > 0) {
                    try {
                        Thread.sleep(st - cos);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                MFGT.finish(SplashActivity.this);
            }
        }).start();


    }


}
