package com.example.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fulicenter.I;
import com.example.fulicenter.R;
import com.example.fulicenter.bean.BoutiqueBean;
import com.example.fulicenter.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 涛 on 2016/10/20.
 */

public class BoutiqueAdapter extends Adapter {
    Context mContext;
    ArrayList<BoutiqueBean> mList;
    boolean isMore;

    public BoutiqueAdapter(Context mContext, ArrayList<BoutiqueBean> list) {
        this.mContext = mContext;
        this.mList = new ArrayList<>();
        mList.addAll(list);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new GoodsAdapter.FooterViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_footer, parent, false));
        } else {
            holder = new BoutiqueViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_boutique, parent, false));
        }
        return holder;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GoodsAdapter.FooterViewHolder){
            ((BoutiqueViewHolder) holder).mTvFooter.setText(getFooterString());
        }
        if (holder instanceof BoutiqueViewHolder){
            BoutiqueBean boutiqueBean= mList.get(position);
            ImageLoader.downloadImg(mContext,((BoutiqueViewHolder) holder).mIvBoutiqueImg,boutiqueBean.getImageurl());
            ((BoutiqueViewHolder) holder).mTvBoutiqueTitle.setText(boutiqueBean.getTitle());
            ((BoutiqueViewHolder) holder).mTvBoutiqueName.setText(boutiqueBean.getName());
            ((BoutiqueViewHolder) holder).mTvBoutiqueDescription.setText(boutiqueBean.getDescription());
        }
    }

    private int getFooterString() {
        return isMore?R.string.load_more:R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;

    }

   

    static class BoutiqueViewHolder extends ViewHolder {
        @BindView(R.id.ivBoutiqueImg)
        ImageView mIvBoutiqueImg;
        @BindView(R.id.tvBoutiqueTitle)
        TextView mTvBoutiqueTitle;
        @BindView(R.id.tvBoutiqueName)
        TextView mTvBoutiqueName;
        @BindView(R.id.tvBoutiqueDescription)
        TextView mTvBoutiqueDescription;
        @BindView(R.id.layout_boutique_item)
        RelativeLayout mLayoutBoutiqueItem;

        BoutiqueViewHolder (View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
