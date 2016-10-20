package com.example.fulicenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fulicenter.I;
import com.example.fulicenter.R;
import com.example.fulicenter.bean.AlbumsBean;
import com.example.fulicenter.bean.GoodsDetailsBean;
import com.example.fulicenter.net.NetDao;
import com.example.fulicenter.net.OkHttpUtils;
import com.example.fulicenter.utils.CommonUtils;
import com.example.fulicenter.utils.L;
import com.example.fulicenter.utils.MFGT;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 涛 on 2016/10/20.
 */

public class BoutiqueActivity extends AppCompatActivity {

    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.tv_good_name_english)
    TextView tvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView tvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView tvGoodPriceCurrent;
    @BindView(R.id.salv)
    com.example.fulicenter.Views.SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    com.example.fulicenter.Views.FlowIndicator indicator;
    @BindView(R.id.wv_good_brief)
    WebView wvGoodBrief;

    int goodsID;
    GoodsDetailActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        ButterKnife.bind(this);
        goodsID = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("details", "goodsid=" + goodsID);
        if (goodsID==0){
            finish();
        }
        mContext=this;
        initView();
        initData();
        setListener();
    }

    private void setListener() {
    }

    private void initData() {
        NetDao.downloadGoodsDetails(mContext, goodsID, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i("details="+result);
                if (result!=null){
                    showGoodDetails(result);
                }else {
                    finish();
                }
            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details,error="+error);
                CommonUtils.showLongToast(error);
            }
        });
    }

    private void showGoodDetails(GoodsDetailsBean details) {
        tvGoodNameEnglish.setText(details.getGoodsEnglishName());
        tvGoodName.setText(details.getGoodsName());
        tvGoodPriceCurrent.setText(details.getCurrencyPrice());
        tvGoodPriceShop.setText(details.getShopPrice());
        salv.startPlayLoop(indicator,getAlbumImgUrl(details),getAlbumImgCount(details));
        wvGoodBrief.loadDataWithBaseURL(null,details.getGoodsBrief(),I.TEXT_HTML,I.UTF_8,null);
    }

    private int getAlbumImgCount(GoodsDetailsBean details) {
        if (details.getProperties()!=null&&details.getProperties().length>0){
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;
    }

    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[]urls=new String []{};
        if (details.getPromotePrice()!=null&&details.getProperties().length>0){
            AlbumsBean[] albums=details.getProperties()[0].getAlbums();
            urls=new String[albums.length];
            for (int i=0;i<albums.length;i++){
                urls[i]=albums[i].getImgUrl();
            }
        }
        return urls;
    }

    private void initView() {
    }
    @OnClick(R.id.backClickArea)
    public void onBackClick(){
        MFGT.finish(this);
    }
    public void onback(View v){
        MFGT.finish(this);
    }
}
