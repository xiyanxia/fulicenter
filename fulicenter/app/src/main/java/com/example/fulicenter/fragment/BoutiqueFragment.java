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

import com.example.fulicenter.I;
import com.example.fulicenter.R;
import com.example.fulicenter.Views.SpaceItemDecoration;
import com.example.fulicenter.bean.NewGoodsBean;
import com.example.fulicenter.net.NetDao;
import com.example.fulicenter.net.OkHttpUtils;
import com.example.fulicenter.utils.CommonUtils;
import com.example.fulicenter.utils.ConvertUtils;
import com.example.fulicenter.utils.L;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 涛 on 2016/10/20.
 */

public class BoutiqueFragment extends Fragment {
    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.rvNewGoods)
    RecyclerView mRvNewGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        initView();
        return layout;
    }
    private void initView() {
        sfl.setColorSchemeColors(getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_yellow),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_green));
        mGridLayoutManager = new GridLayoutManager(mContext, I.COLUM_NUM);
        rlv.setLayoutManager(mGridLayoutManager);
        rlv.setHasFixedSize(true);
        rlv.setAdapter(mAdapter);
        rlv.addItemDecoration(new SpaceItemDecoration(12));
    }
    private void downLoadData(final int action) {
        NetDao.downloadGoods(mContext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                sfl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                if(result!=null && result.length>0){
                    ArrayList<NewGoodsBean> list= ConvertUtils.array2List(result);
                    if( action == I.ACTION_DOWNLOAD ||action==I.ACTION_PULL_DOWN){
                        mAdapter.initData(list);
                    }else {
                        mAdapter.addData(list);
                    }

                    if(list.size()< I.PAGE_SIZE_DEFAULT){
                        mAdapter.setMore(false);
                    }else
                        mAdapter.setMore(true);
                }
            }
            @Override
            public void onError(String error) {
                sfl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                CommonUtils.showLongToast(error);
                L.e("error: "+error);

            }
        });
    }
}
