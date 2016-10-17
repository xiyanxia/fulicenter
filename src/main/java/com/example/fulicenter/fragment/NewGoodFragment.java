package com.example.fulicenter.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fulicenter.Activity.MainActivity;
import com.example.fulicenter.I;
import com.example.fulicenter.R;
import com.example.fulicenter.adpter.GoodAdpter;
import com.example.fulicenter.bean.NewGoodsBean;
import com.example.fulicenter.net.NetDao;
import com.example.fulicenter.net.OkHttpUtils;
import com.example.fulicenter.utils.ConvertUtils;
import com.example.fulicenter.utils.L;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 涛 on 2016/10/17.
 */

public class NewGoodFragment extends Fragment {
    @BindView(R.id.tv_refresh)
    TextView mtvRefresh;
    @BindView(R.id.rv)
    RecyclerView mrv;
    @BindView(R.id.srl)
    SwipeRefreshLayout msrl;

    MainActivity mContext;
    GoodAdpter mAdapter;
    ArrayList<NewGoodsBean> mLsit;
    int pageId=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgood, container, false);
        ButterKnife.bind(this, layout);
       mContext=(MainActivity) getContext();
        mLsit=new ArrayList<>();
        mAdapter=new GoodAdpter(mContext,mLsit);
        initView();
        initData();
        return layout;
    }

    private void initData() {
        NetDao.downloadNewGoodsList(mContext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if (result!=null&&result.length>0){
                    ArrayList<NewGoodsBean> list=ConvertUtils.array2List(result);
                    mAdapter.initData(list);
                }
            }

            @Override
            public void onError(String error) {
                L.e("error"+error);
            }
        });
    }

    private void initView() {
        msrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        GridLayoutManager glm=new GridLayoutManager(mContext, I.COLUM_NUM);
        mrv.setLayoutManager(glm);
        mrv.setHasFixedSize(true);
        mrv.setAdapter(mAdapter);
    }
}
