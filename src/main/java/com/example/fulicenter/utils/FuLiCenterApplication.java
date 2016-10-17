package com.example.fulicenter.utils;

import android.app.Application;

/**
 * Created by 涛 on 2016/10/17.
 */
public class FuLiCenterApplication extends Application{
    private static FuLiCenterApplication instance;
    public FuLiCenterApplication(){
        instance=this;
    }
    public static FuLiCenterApplication getInstance(){
        if (instance==null){
            instance=new FuLiCenterApplication();
        }
        return instance;
    }

}
