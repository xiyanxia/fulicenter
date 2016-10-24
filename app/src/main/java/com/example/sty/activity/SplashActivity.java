package com.example.sty.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.sty.FuLiCenterApplication;
import com.example.sty.R;
import com.example.sty.bean.User;
import com.example.sty.dao.SharePrefrenceUtils;
import com.example.sty.dao.UserDao;
import com.example.sty.utils.L;
import com.example.sty.utils.MFGT;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG=SplashActivity.class.getSimpleName();
    private static final long sleepTime=2000;
    SplashActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user=FuLiCenterApplication.getUser();
                String username=SharePrefrenceUtils.getInstence(mContext).getUser();
                L.e(TAG,"fulicenter.user="+username);
                if (user==null&&username!=null) {
                    UserDao dao = new UserDao(mContext);
                    user = dao.getUser(username);
                    L.e(TAG,"user="+user);
                    if (user!=null){
                        FuLiCenterApplication.setUser(user);
                    }
                }
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();
            }
        },sleepTime);
    }
}
