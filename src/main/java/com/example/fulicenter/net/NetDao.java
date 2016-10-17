package com.example.fulicenter.net;

import android.content.Context;

import com.example.fulicenter.I;
import com.example.fulicenter.bean.NewGoodsBean;

/**
 * Created by 涛 on 2016/10/17.
 */
public class NetDao {
    public static void downloadNewGoodsList(Context context, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]>listener){
        OkHttpUtils<NewGoodsBean[]> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS)
                .addParam(I.NewAndBoutiqueGoods.CAT_ID,I.CAT_ID+"")
                .addParam(I.PAGE_ID,pageId+"")
                .addParam(I.PAGE_SIZE,I.PAGE_SIZE_DEFAULT+"")
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
}
