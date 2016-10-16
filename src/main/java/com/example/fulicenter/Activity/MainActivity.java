package com.example.fulicenter.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.fulicenter.R;
import com.example.fulicenter.utils.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.i("MainActivity onCreate");
    }


}