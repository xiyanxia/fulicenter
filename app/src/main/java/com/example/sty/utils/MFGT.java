package com.example.sty.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.sty.I;
import com.example.sty.R;
import com.example.sty.activity.BoutiqueChildActivity;
import com.example.sty.activity.CategoryChildActivity;
import com.example.sty.activity.GoodsDetailActivity;
import com.example.sty.activity.LoginActivity;
import com.example.sty.activity.MainActivity;
import com.example.sty.activity.RegisterActivity;
import com.example.sty.bean.BoutiqueBean;
import com.example.sty.bean.CategoryChildBean;

import java.util.ArrayList;

/**
 * Created by Winston on 2016/10/14.
 * 辅助跳转
 */
public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        startActivity(context,intent);
    }

    public static void gotoGoodsDetailsActivity(Context context, int goodsId){
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId);
        startActivity(context,intent);
    }

    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }


    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean bean){
        Intent intent = new Intent();
        intent.setClass(context, BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID,bean);
        startActivity(context,intent);
    }
    public static void gotoCategoryChildActivity(Context context, int catId, String groupName, ArrayList<CategoryChildBean> list){
        Intent intent = new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.CategoryChild.CAT_ID,catId);
        intent.putExtra(I.CategoryGroup.NAME,groupName);
        intent.putExtra(I.CategoryChild.ID, list);
        startActivity(context,intent);
    }
    public static void gotoRegister(Activity context){
        Intent intent= new Intent();
        intent.setClass(context,RegisterActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_REGISTER);
        startActivity(context, RegisterActivity.class);
    }
    public static void startActivityForResult(Activity context,Intent intent,int requestCode){
        context.startActivityForResult(intent,requestCode);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
public static void gotoLogin(Activity context){
    startActivity(context, LoginActivity.class);
}


}

