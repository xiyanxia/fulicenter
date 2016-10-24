package com.example.sty.dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 涛 on 2016/10/24.
 */

public class SharePrefrenceUtils {
    private static final String SHARE_NAME="saveUserInfo";
    private static SharePrefrenceUtils instance;
    private SharedPreferences mSharePrefrenceUtils;
    private SharedPreferences.Editor mEditor;
    public static final String SHARE_KEY_USER_NAME="share_key_user_name";

    public SharePrefrenceUtils(Context context){
        mSharePrefrenceUtils=context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        mEditor=mSharePrefrenceUtils.edit();
    }
    public static SharePrefrenceUtils getInstence(Context context){
        if (instance==null){
            instance=new SharePrefrenceUtils(context);
        }
        return instance;
    }
    public void saveUser(String username){
        mEditor.putString(SHARE_KEY_USER_NAME,username);
        mEditor.commit();
    }
    public String getUser(){
          return mSharePrefrenceUtils.getString(SHARE_KEY_USER_NAME,null);
    }
    public void removeUser(){
        mEditor.remove(SHARE_KEY_USER_NAME);
        mEditor.commit();
    }

}
